package com.prof18.moneyflow.data

import co.touchlab.kermit.Logger
import com.prof18.moneyflow.data.db.DatabaseSource
import com.prof18.moneyflow.data.dropbox.DropboxSource
import com.prof18.moneyflow.data.settings.SettingsSource
import com.prof18.moneyflow.db.DropboxMetadataTable
import com.prof18.moneyflow.domain.entities.DatabaseDownloadData
import com.prof18.moneyflow.domain.entities.DatabaseUploadData
import com.prof18.moneyflow.domain.entities.DropboxAuthFailedExceptions
import com.prof18.moneyflow.domain.entities.DropboxClientStatus
import com.prof18.moneyflow.domain.entities.DropboxSyncMetadata
import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.domain.mapper.toDropboxDownloadParams
import com.prof18.moneyflow.domain.mapper.toDropboxUploadParams
import com.prof18.moneyflow.domain.repository.DropboxSyncRepository
import com.prof18.moneyflow.dropboxapi.DropboxAuthorizationParam
import com.prof18.moneyflow.dropboxapi.DropboxClient
import com.prof18.moneyflow.dropboxapi.DropboxCredentials
import com.prof18.moneyflow.dropboxapi.DropboxDownloadException
import com.prof18.moneyflow.dropboxapi.DropboxException
import com.prof18.moneyflow.dropboxapi.DropboxSetupParam
import com.prof18.moneyflow.dropboxapi.DropboxUploadException
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.utils.DispatcherProvider
import com.prof18.moneyflow.utils.DropboxConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

class DropboxSyncRepositoryImpl(
    private val dropboxSource: DropboxSource,
    private val settingsSource: SettingsSource,
    private val dispatcherProvider: DispatcherProvider,
    private val errorMapper: MoneyFlowErrorMapper,
    private val databaseSource: DatabaseSource,
) : DropboxSyncRepository {

    private val _dropboxConnectionStatus = MutableStateFlow(DropboxClientStatus.NOT_LINKED)
    override val dropboxConnectionStatus: StateFlow<DropboxClientStatus>
        get() = _dropboxConnectionStatus

    private var dropboxMetadataFlow: Flow<DropboxMetadataTable> = emptyFlow()

    private var dropboxClient: DropboxClient? = null

    init {
        dropboxMetadataFlow = databaseSource.getDropboxMetadata()
    }

    override fun setupDropboxApp(setupParam: DropboxSetupParam) {
        Logger.d { "Setting up dropbox app" }
        dropboxSource.setup(setupParam)
    }

    override fun startDropboxAuthorization(authorizationParam: DropboxAuthorizationParam) {
        Logger.d { "Starting dropbox oauth flow" }
        dropboxSource.startAuthorization(authorizationParam)
    }

    // TODO: move in the constructor, if necessary
    override suspend fun restoreDropboxClient(): MoneyFlowResult<Unit> = withContext(dispatcherProvider.default()) {
        if (dropboxClient != null) {
            // Avoid setting up again
            return@withContext MoneyFlowResult.Success(Unit)
        }
        val savedStringCredentials =
            settingsSource.getDropboxClientCred() ?: return@withContext generateDropboxAuthErrorResult()
        val credentials = try {
            dropboxSource.getCredentialsFromString(savedStringCredentials)
        } catch (e: Exception) {
            Logger.e("Restore credentials failed", e)
            null
        }
        if (credentials != null) {
            setClient(credentials)
            if (dropboxClient == null) {
                return@withContext generateDropboxAuthErrorResult()
            }
        }
        return@withContext MoneyFlowResult.Success(Unit)
    }

    override suspend fun saveDropboxAuthorization(): MoneyFlowResult<Unit> = withContext(dispatcherProvider.default()) {
        val credentials = dropboxSource.getCredentials() ?: return@withContext generateDropboxAuthErrorResult()
        val stringCredentials = credentials.toString()
        settingsSource.saveDropboxClientCred(stringCredentials)
        setClient(credentials)
        if (dropboxClient == null) {
            return@withContext generateDropboxAuthErrorResult()
        }
        return@withContext MoneyFlowResult.Success(Unit)
    }

    override suspend fun unlinkDropboxClient() = withContext(dispatcherProvider.default()) {
        dropboxClient?.let {
            try {
                dropboxSource.revokeAccess(it)
            } catch (_: DropboxException) {

            }
        }
        dropboxClient = null
        databaseSource.resetDropboxMetadata()
        settingsSource.deleteDropboxClientCred()
        _dropboxConnectionStatus.value = DropboxClientStatus.NOT_LINKED
    }

    override suspend fun upload(
        databaseUploadData: DatabaseUploadData,
    ): MoneyFlowResult<Unit> = withContext(dispatcherProvider.default()) {

        if (dropboxClient == null) {
            generateDropboxAuthErrorResult()
        }
        try {
            val result = dropboxSource.performUpload(databaseUploadData.toDropboxUploadParams(dropboxClient!!))
            databaseSource.insertLatestDropboxUploadTime(result.editDateMillis)
            databaseSource.insertLatestDropboxUploadHash(result.contentHash)
            return@withContext MoneyFlowResult.Success(Unit)
        } catch (e: DropboxUploadException) {
            Logger.e("Upload to dropbox failed", e)
            val error = MoneyFlowError.DropboxUpload(e)
            return@withContext MoneyFlowResult.Error(errorMapper.getUIErrorMessage(error))
        }

    }

    override suspend fun download(
        databaseDownloadData: DatabaseDownloadData,
    ): MoneyFlowResult<Unit> = withContext(dispatcherProvider.default()) {
        if (dropboxClient == null) {
            generateDropboxAuthErrorResult()
        }
        try {
            val result = dropboxSource.performDownload(databaseDownloadData.toDropboxDownloadParams(dropboxClient!!))
            Logger.d { "result: $result" }
            databaseSource.insertLatestDropboxDownloadHash(result.contentHash)
            databaseSource.insertLatestDropboxDownloadTime(Clock.System.now().toEpochMilliseconds())
            return@withContext MoneyFlowResult.Success(Unit)
        } catch (e: DropboxDownloadException) {
            Logger.e("Download from dropbox failed", e)
            val error = MoneyFlowError.DropboxDownload(e)
            return@withContext MoneyFlowResult.Error(errorMapper.getUIErrorMessage(error))
        }
    }

    override fun getDropboxSyncMetadata(): Flow<DropboxSyncMetadata> {
        return dropboxMetadataFlow.map {
            DropboxSyncMetadata(
                lastUploadTimestamp = it.lastUploadTimestamp,
                lastDownloadTimestamp = it.lastDownloadTimestamp,
                lastUploadHash = it.lastUploadHash,
                lastDownloadHash = it.lastDownloadHash,
            )
        }
    }

    private fun setClient(credentials: DropboxCredentials) {
        dropboxClient = dropboxSource.getClient(
            clientIdentifier = DropboxConstants.DROPBOX_CLIENT_IDENTIFIER,
            credentials = credentials
        )
        if (dropboxClient != null) {
            _dropboxConnectionStatus.value = DropboxClientStatus.LINKED
        }
    }

    private fun generateDropboxAuthErrorResult(): MoneyFlowResult.Error {
        val error = MoneyFlowError.DropboxAuth(DropboxAuthFailedExceptions())
        return MoneyFlowResult.Error(errorMapper.getUIErrorMessage(error))
    }
}
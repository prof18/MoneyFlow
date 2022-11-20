package com.prof18.moneyflow.data

import co.touchlab.kermit.Logger
import com.prof18.moneyflow.data.dropbox.DropboxSource
import com.prof18.moneyflow.data.settings.SettingsSource
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
import com.prof18.moneyflow.dropbox.DropboxAuthorizationParam
import com.prof18.moneyflow.dropbox.DropboxClient
import com.prof18.moneyflow.dropbox.DropboxCredentials
import com.prof18.moneyflow.dropbox.DropboxDownloadException
import com.prof18.moneyflow.dropbox.DropboxDownloadResult
import com.prof18.moneyflow.dropbox.DropboxException
import com.prof18.moneyflow.dropbox.DropboxHandleOAuthRequestParam
import com.prof18.moneyflow.dropbox.DropboxSetupParam
import com.prof18.moneyflow.dropbox.DropboxUploadException
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.utils.DispatcherProvider
import com.prof18.moneyflow.utils.DropboxConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

@Suppress("TooManyFunctions") // TODO: Reduce the number?
internal class DropboxSyncRepositoryImpl(
    private val dropboxSource: DropboxSource,
    private val settingsSource: SettingsSource,
    private val dispatcherProvider: DispatcherProvider,
    private val errorMapper: MoneyFlowErrorMapper,
) : DropboxSyncRepository {

    private val _dropboxConnectionStatus = MutableStateFlow(DropboxClientStatus.NOT_LINKED)
    override val dropboxConnectionStatus: StateFlow<DropboxClientStatus>
        get() = _dropboxConnectionStatus

    private var dropboxClient: DropboxClient? = null

    override val dropboxMetadataFlow: StateFlow<DropboxSyncMetadata>
        get() = _dropboxMetadataFlow

    private var _dropboxMetadataFlow = MutableStateFlow(DropboxSyncMetadata.empty())

    private var currentDropboxMetadata: DropboxSyncMetadata = DropboxSyncMetadata.empty()

    override fun setupDropboxApp(setupParam: DropboxSetupParam) {
        Logger.d { "Setting up dropbox app" }
        dropboxSource.setup(setupParam)
    }

    override fun handleOAuthResponse(oAuthRequestParam: DropboxHandleOAuthRequestParam) {
        dropboxSource.handleOAuthResponse(oAuthRequestParam)
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
        val savedStringCredentials = settingsSource.getDropboxClientCred()
            ?: return@withContext generateDropboxAuthErrorResult()
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

        setAndEmitDropboxMetadata(getSavedDropboxMetadata())

        return@withContext MoneyFlowResult.Success(Unit)
    }

    private fun getSavedDropboxMetadata() = DropboxSyncMetadata(
        lastUploadTimestamp = settingsSource.getLastDropboxUploadTimestamp(),
        lastDownloadTimestamp = settingsSource.getLastDropboxDownloadTimestamp(),
        lastUploadHash = settingsSource.getLastDropboxUploadHash(),
        lastDownloadHash = settingsSource.getLastDropboxDownloadHash(),
    )

    override suspend fun saveDropboxAuthorization(): MoneyFlowResult<Unit> = withContext(dispatcherProvider.default()) {
        val credentials = dropboxSource.getCredentials()
        val stringCredentials = credentials.toString()
        settingsSource.saveDropboxClientCred(stringCredentials)
        credentials?.let { setClient(it) }
        if (dropboxClient == null) {
            return@withContext generateDropboxAuthErrorResult()
        }
        setAndEmitDropboxMetadata(getSavedDropboxMetadata())
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
        settingsSource.clearDropboxData()
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

            settingsSource.setLastDropboxUploadTimestamp(result.editDateMillis)
            settingsSource.setLastDropboxUploadHash(result.contentHash)
            setAndEmitDropboxMetadata(
                currentDropboxMetadata.copy(
                    lastUploadTimestamp = result.editDateMillis,
                    lastUploadHash = result.contentHash,
                ),
            )
            return@withContext MoneyFlowResult.Success(Unit)
        } catch (e: DropboxUploadException) {
            Logger.e("Upload to dropbox failed", e)
            val error = MoneyFlowError.DropboxUpload(e)
            return@withContext MoneyFlowResult.Error(errorMapper.getUIErrorMessage(error))
        }
    }

    override suspend fun download(
        databaseDownloadData: DatabaseDownloadData,
    ): MoneyFlowResult<DropboxDownloadResult> = withContext(dispatcherProvider.default()) {
        if (dropboxClient == null) {
            generateDropboxAuthErrorResult()
        }
        try {
            val result = dropboxSource.performDownload(databaseDownloadData.toDropboxDownloadParams(dropboxClient!!))

            val millis = Clock.System.now().toEpochMilliseconds()
            settingsSource.setLastDropboxDownloadTimestamp(millis)
            settingsSource.setLastDropboxDownloadHash(result.contentHash)
            setAndEmitDropboxMetadata(
                currentDropboxMetadata.copy(
                    lastDownloadTimestamp = millis,
                    lastDownloadHash = result.contentHash,
                ),
            )
            return@withContext MoneyFlowResult.Success(result)
        } catch (e: DropboxDownloadException) {
            Logger.e("Download from dropbox failed", e)
            val error = MoneyFlowError.DropboxDownload(e)
            return@withContext MoneyFlowResult.Error(errorMapper.getUIErrorMessage(error))
        }
    }

    private fun setClient(credentials: DropboxCredentials) {
        dropboxClient = dropboxSource.getClient(
            clientIdentifier = DropboxConstants.DROPBOX_CLIENT_IDENTIFIER,
            credentials = credentials,
        )
        if (dropboxClient != null) {
            _dropboxConnectionStatus.value = DropboxClientStatus.LINKED
        }
    }

    private fun generateDropboxAuthErrorResult(): MoneyFlowResult.Error {
        val error = MoneyFlowError.DropboxAuth(DropboxAuthFailedExceptions())
        return MoneyFlowResult.Error(errorMapper.getUIErrorMessage(error))
    }

    private suspend fun setAndEmitDropboxMetadata(dropboxSyncMetadata: DropboxSyncMetadata) {
        currentDropboxMetadata = dropboxSyncMetadata
        _dropboxMetadataFlow.emit(currentDropboxMetadata)
    }
}

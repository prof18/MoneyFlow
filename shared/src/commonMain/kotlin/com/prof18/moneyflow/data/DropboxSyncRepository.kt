package com.prof18.moneyflow.data

import co.touchlab.kermit.Logger
import com.prof18.moneyflow.data.settings.SettingsSource
import com.prof18.moneyflow.domain.entities.DropboxAuthFailedExceptions
import com.prof18.moneyflow.domain.entities.DropboxClientStatus
import com.prof18.moneyflow.domain.entities.DropboxSyncMetadata
import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.dropbox.DropboxDataSource
import com.prof18.moneyflow.dropbox.DropboxDownloadParam
import com.prof18.moneyflow.dropbox.DropboxDownloadResult
import com.prof18.moneyflow.dropbox.DropboxStringCredentials
import com.prof18.moneyflow.dropbox.DropboxUploadParam
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.utils.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

@Suppress("TooManyFunctions") // TODO: Reduce the number?
class DropboxSyncRepository(
    private val dropboxDataSource: DropboxDataSource,
    private val settingsSource: SettingsSource,
    private val dispatcherProvider: DispatcherProvider,
    private val errorMapper: MoneyFlowErrorMapper,
) {

    private val _dropboxConnectionStatus = MutableStateFlow(DropboxClientStatus.NOT_LINKED)
    val dropboxConnectionStatus: StateFlow<DropboxClientStatus>
        get() = _dropboxConnectionStatus

    val dropboxMetadataFlow: StateFlow<DropboxSyncMetadata>
        get() = _dropboxMetadataFlow

    private var _dropboxMetadataFlow = MutableStateFlow(DropboxSyncMetadata.empty())

    private var currentDropboxMetadata: DropboxSyncMetadata = DropboxSyncMetadata.empty()

    fun setupDropboxApp(apiKey: String) {
        Logger.d { "Setting up dropbox app" }
        dropboxDataSource.setup(apiKey)
    }

    fun handleOAuthResponse(platformOAuthResponseHandler: () -> Unit) {
        dropboxDataSource.handleOAuthResponse(platformOAuthResponseHandler)
    }

    fun startDropboxAuthorization(platformAuthHandler: () -> Unit) {
        Logger.d { "Starting dropbox oauth flow" }
        dropboxDataSource.startAuthorization(platformAuthHandler)
    }

    // TODO: move in the constructor, if necessary
    suspend fun restoreDropboxClient(): MoneyFlowResult<Unit> =
        withContext(dispatcherProvider.default()) {
            val savedStringCredentials = settingsSource.getDropboxClientCred()
                ?: return@withContext generateDropboxAuthErrorResult()

            dropboxDataSource.restoreAuth(
                DropboxStringCredentials(
                    value = savedStringCredentials,
                ),
            )

            if (dropboxDataSource.isClientSet()) {
                _dropboxConnectionStatus.value = DropboxClientStatus.LINKED
            } else {
                return@withContext generateDropboxAuthErrorResult()
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

    suspend fun saveDropboxAuthorization(): MoneyFlowResult<Unit> =
        withContext(dispatcherProvider.default()) {
            val stringCredentials = dropboxDataSource.saveAuth()
            settingsSource.saveDropboxClientCred(stringCredentials.value)

            if (dropboxDataSource.isClientSet()) {
                _dropboxConnectionStatus.value = DropboxClientStatus.LINKED
            } else {
                return@withContext generateDropboxAuthErrorResult()
            }

            setAndEmitDropboxMetadata(getSavedDropboxMetadata())
            return@withContext MoneyFlowResult.Success(Unit)
        }

    suspend fun unlinkDropboxClient() = withContext(dispatcherProvider.default()) {
        dropboxDataSource.revokeAccess()
        settingsSource.clearDropboxData()
        _dropboxConnectionStatus.value = DropboxClientStatus.NOT_LINKED
    }

    suspend fun upload(
        dropboxUploadParam: DropboxUploadParam,
    ): MoneyFlowResult<Unit> = withContext(dispatcherProvider.default()) {

        if (!dropboxDataSource.isClientSet()) {
            generateDropboxAuthErrorResult()
        }

        try {
            val result = dropboxDataSource.performUpload(dropboxUploadParam)

            settingsSource.setLastDropboxUploadTimestamp(result.editDateMillis)
            settingsSource.setLastDropboxUploadHash(result.contentHash)
            setAndEmitDropboxMetadata(
                currentDropboxMetadata.copy(
                    lastUploadTimestamp = result.editDateMillis,
                    lastUploadHash = result.contentHash,
                ),
            )
            return@withContext MoneyFlowResult.Success(Unit)
        } catch (e: Exception) {
            Logger.e("Upload to dropbox failed", e)
            val error = MoneyFlowError.DropboxUpload(e)
            return@withContext MoneyFlowResult.Error(errorMapper.getUIErrorMessage(error))
        }
    }

    suspend fun download(
        dropboxDownloadParam: DropboxDownloadParam,
    ): MoneyFlowResult<DropboxDownloadResult> = withContext(dispatcherProvider.default()) {
        if (!dropboxDataSource.isClientSet()) {
            generateDropboxAuthErrorResult()
        }

        try {
            val result = dropboxDataSource.performDownload(dropboxDownloadParam)
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
        } catch (e: Exception) {
            Logger.e("Download from dropbox failed", e)
            val error = MoneyFlowError.DropboxDownload(e)
            return@withContext MoneyFlowResult.Error(errorMapper.getUIErrorMessage(error))
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

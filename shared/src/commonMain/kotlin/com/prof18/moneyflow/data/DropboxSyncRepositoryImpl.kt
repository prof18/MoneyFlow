package com.prof18.moneyflow.data

import co.touchlab.kermit.Logger
import com.prof18.moneyflow.data.dropbox.DropboxSource
import com.prof18.moneyflow.data.settings.SettingsSource
import com.prof18.moneyflow.domain.entities.DropboxAuthFailedExceptions
import com.prof18.moneyflow.domain.entities.DropboxClientStatus
import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.domain.repository.DropboxSyncRepository
import com.prof18.moneyflow.dropboxapi.*
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.domain.entities.DatabaseData
import com.prof18.moneyflow.domain.mapper.toDropboxUploadParams
import com.prof18.moneyflow.utils.DispatcherProvider
import com.prof18.moneyflow.utils.DropboxConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class DropboxSyncRepositoryImpl(
    private val dropboxSource: DropboxSource,
    private val settingsSource: SettingsSource,
    private val dispatcherProvider: DispatcherProvider,
    private val errorMapper: MoneyFlowErrorMapper,
) : DropboxSyncRepository {

    private val _dropboxConnectionStatus = MutableStateFlow(DropboxClientStatus.NOT_LINKED)
    override val dropboxConnectionStatus: StateFlow<DropboxClientStatus>
        get() = _dropboxConnectionStatus

    private val _lastDropboxSync = MutableStateFlow<Long?>(null)
    override val lastDropboxSync: StateFlow<Long?>
        get() = _lastDropboxSync

    private var dropboxClient: DropboxClient? = null

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
        settingsSource.deleteLastDropboxSync()
        settingsSource.deleteDropboxClientCred()
        _dropboxConnectionStatus.value = DropboxClientStatus.NOT_LINKED
        _lastDropboxSync.value = null
    }

    override suspend fun upload(
        databaseData: DatabaseData,
    ): MoneyFlowResult<Unit> = withContext(dispatcherProvider.default()) {

        if (dropboxClient == null) {
            // TODO: return a custom error to the caller!
            TODO()
//            return@withContext MoneyFlowResult.Error()
        }
        try {
            // TODO: return and map the result in some way
            val result = dropboxSource.performUpload(databaseData.toDropboxUploadParams(dropboxClient!!))
            /*result.serverModified?.time?.let { lastRefresh ->
                Timber.d("Last refresh new: $lastRefresh")
                dropboxSyncUserCase.saveLastRefresh(lastRefresh)
//                    dropboxSyncUserCase.updateLastRefreshSuspendable(lastRefresh)
            }*/
        } catch (e: DropboxUploadException) {
            Logger.e("Error", e)
            TODO()
            // Return a custom error to the caller!
//            return@withContext MoneyFlowResult.Error()
        }




        return@withContext MoneyFlowResult.Success(Unit)
    }

    suspend fun download() = withContext(dispatcherProvider.default()) {
        // TODO: return something as soon understood android side
    }

    private fun setClient(credentials: DropboxCredentials) {
        dropboxClient = dropboxSource.getClient(
            clientIdentifier = DropboxConstants.DROPBOX_CLIENT_IDENTIFIER,
            credentials = credentials
        )
        if (dropboxClient != null) {
            _lastDropboxSync.value = settingsSource.getDropboxLastSync()
            _dropboxConnectionStatus.value = DropboxClientStatus.LINKED
        }
    }

    private fun generateDropboxAuthErrorResult(): MoneyFlowResult.Error {
        val error = MoneyFlowError.DropboxAuth(DropboxAuthFailedExceptions())
        return MoneyFlowResult.Error(errorMapper.getUIErrorMessage(error))
    }

    companion object {

        // TODO: enable it when necessary!
//        private const val UPLOAD_FILE_SIZE_LIMIT: Long = 150 // MB
//        private const val LARGE_FILE = "File larger then $UPLOAD_FILE_SIZE_LIMIT MB"
    }
}
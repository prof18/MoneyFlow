package com.prof18.moneyflow.data

import co.touchlab.kermit.Logger
import com.prof18.moneyflow.data.dropbox.DropboxSource
import com.prof18.moneyflow.data.settings.SettingsSource
import com.prof18.moneyflow.domain.entities.DropboxAuthFailedException
import com.prof18.moneyflow.domain.entities.DropboxClientStatus
import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.domain.repository.DropboxSyncRepository
import com.prof18.moneyflow.dropboxapi.*
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.utils.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class DropboxSyncRepositoryImpl(
    private val dropboxSource: DropboxSource,
    private val settingsSource: SettingsSource,
    private val dispatcherProvider: DispatcherProvider,
    private val errorMapper: MoneyFlowErrorMapper
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

    // TODo: move in the constructor, if necessary
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

    suspend fun upload(): MoneyFlowResult<Unit> = withContext(dispatcherProvider.default()) {

        // TODO: implement. Maybe use a common class to get the database class

        /*
         val databaseFile = databaseImportExport.generateDatabaseFile()
        if (databaseFile != null) {
            try {
                val metadata = dbxClientV2?.files()
                    ?.uploadBuilder("/MoneyFlow.db")
                    ?.withMode(WriteMode.OVERWRITE)
                    ?.uploadAndFinish(FileInputStream(databaseFile))

//                val rev = metadata?.rev
                metadata?.serverModified?.time?.let { lastRefresh ->
                    Timber.d("Last refresh new: $lastRefresh")
                    // TODO: improve and set a flow to show the update
                    dropboxSyncUserCase.saveLastRefresh(lastRefresh)
//                    dropboxSyncUserCase.updateLastRefreshSuspendable(lastRefresh)
                }

                Timber.d("Upload Done")
                continuation.resume(Unit)

            } catch (e: DbxException) {
                Timber.e("Unable to upload backup on dropbox")
                e.printStackTrace()
                continuation.resumeWithException(e)
            }
        }

        Timber.d("file null?: ${if (databaseFile == null) "yes" else "no"}")
         */

        return@withContext MoneyFlowResult.Success(Unit)
    }

    suspend fun download() = withContext(dispatcherProvider.default()) {
        // TODO: return something as soon understood android side
    }

    private fun setClient(credentials: DropboxCredentials) {
        dropboxClient = dropboxSource.getClient(
            clientIdentifier = DROPBOX_CLIENT_IDENTIFIER,
            credentials = credentials
        )
        if (dropboxClient != null) {
            _lastDropboxSync.value = settingsSource.getDropboxLastSync()
            _dropboxConnectionStatus.value = DropboxClientStatus.LINKED
        }
    }

    private fun generateDropboxAuthErrorResult(): MoneyFlowResult.Error {
        val error = MoneyFlowError.DropboxAuth(DropboxAuthFailedException())
        return MoneyFlowResult.Error(errorMapper.getUIErrorMessage(error))
    }

    companion object {
        private const val DROPBOX_CLIENT_IDENTIFIER = "moneyflowapp"

        // TODO: enable it when necessary!
//        private const val UPLOAD_FILE_SIZE_LIMIT: Long = 150 // MB
//        private const val LARGE_FILE = "File larger then $UPLOAD_FILE_SIZE_LIMIT MB"
    }
}
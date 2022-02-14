package com.prof18.moneyflow.features.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof18.moneyflow.data.db.DB_FILE_NAME_WITH_EXTENSION
import com.prof18.moneyflow.database.DBImportExport
import com.prof18.moneyflow.domain.entities.DatabaseData
import com.prof18.moneyflow.domain.entities.DropboxClientStatus
import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.dropboxapi.DropboxAuthorizationParam
import com.prof18.moneyflow.platform.LocalizedStringProvider
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.presentation.dropboxsync.DropboxSyncAction
import com.prof18.moneyflow.presentation.dropboxsync.DropboxSyncTimestampModel
import com.prof18.moneyflow.presentation.dropboxsync.DropboxSyncUseCase
import com.prof18.moneyflow.utils.logError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class DropboxSyncViewModel(
    private val dropboxSyncUseCase: DropboxSyncUseCase,
    private val databaseImportExport: DBImportExport,
    private val errorMapper: MoneyFlowErrorMapper,
    private val localizedStringProvider: LocalizedStringProvider,
) : ViewModel() {

    private val _dropboxSyncTimestampState =
        MutableStateFlow<DropboxSyncTimestampModel>(DropboxSyncTimestampModel.Loading)
    val dropboxSyncTimestampState: StateFlow<DropboxSyncTimestampModel>
        get() = _dropboxSyncTimestampState

    private val _isDropboxConnected = MutableStateFlow(false)
    val isDropboxConnected: StateFlow<Boolean>
        get() = _isDropboxConnected

    var dropboxSyncAction: DropboxSyncAction? by mutableStateOf(null)
        private set

    init {
        viewModelScope.launch {
            dropboxSyncUseCase.restoreDropboxClient()
            dropboxSyncUseCase.dropboxClientStatus.collect {
                Timber.d("Got new client status: $it")
                _isDropboxConnected.value = when (it) {
                    DropboxClientStatus.LINKED -> true
                    DropboxClientStatus.NOT_LINKED -> false
                }
            }
        }
        viewModelScope.launch {
            dropboxSyncUseCase.observeDropboxSyncModel()
                .catch { throwable: Throwable ->
                    val error = MoneyFlowError.DropboxMetadata(throwable)
                    throwable.logError(error)
                    val errorMessage = errorMapper.getUIErrorMessage(error)
                    _dropboxSyncTimestampState.value = DropboxSyncTimestampModel.Error(errorMessage)
                }
                .collect { _dropboxSyncTimestampState.value = it }
        }
    }

    fun startAuthFlow(authorizationParam: DropboxAuthorizationParam) {
        dropboxSyncUseCase.startAuthFlow(authorizationParam)
    }

    fun saveDropboxAuth() {
        viewModelScope.launch {
            val result = dropboxSyncUseCase.saveDropboxAuth()
            if (result is MoneyFlowResult.Error) {
                dropboxSyncAction = DropboxSyncAction.ShowError(result.uiErrorMessage)
            }
        }
    }

    fun resetAction() {
        dropboxSyncAction = null
    }

    fun unlinkDropbox() {
        viewModelScope.launch {
            dropboxSyncUseCase.unlinkDropbox()
        }
    }

    fun backup() {
        viewModelScope.launch {
            dropboxSyncAction = DropboxSyncAction.Loading

            val databaseFile = withContext(Dispatchers.IO) {
                databaseImportExport.generateDatabaseFile()
            }
            if (databaseFile == null) {
                val error = MoneyFlowError.DatabaseNotFound(IllegalArgumentException("Database file is null"))
                dropboxSyncAction = DropboxSyncAction.ShowError(errorMapper.getUIErrorMessage(error))
                return@launch
            }
            val databaseData = DatabaseData(
                path = "/$DB_FILE_NAME_WITH_EXTENSION",
                file = databaseFile
            )
            val result = dropboxSyncUseCase.upload(databaseData)
            dropboxSyncAction = when (result) {
                is MoneyFlowResult.Error -> DropboxSyncAction.ShowError(result.uiErrorMessage)
                is MoneyFlowResult.Success -> DropboxSyncAction.Success(localizedStringProvider.get("dropbox_upload_success"))
            }
        }
    }
}

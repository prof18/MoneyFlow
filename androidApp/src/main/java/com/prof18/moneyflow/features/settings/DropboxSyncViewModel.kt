package com.prof18.moneyflow.features.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dropbox.core.android.Auth
import com.prof18.moneyflow.domain.entities.DropboxClientStatus
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.dropboxapi.DropboxAuthorizationParam
import com.prof18.moneyflow.presentation.addtransaction.AddTransactionAction
import com.prof18.moneyflow.presentation.dropboxsync.DropboxSyncAction
import com.prof18.moneyflow.presentation.dropboxsync.DropboxSyncUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class DropboxSyncViewModel(
    private val dropboxSyncUseCase: DropboxSyncUseCase,
) : ViewModel() {

    private val _lastRefresh = MutableStateFlow<String?>(null)
    val lastRefresh: StateFlow<String?>
        get() = _lastRefresh

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
            // TODO: observe also latest sync
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

    /*fun getLastRefresh() {
        val refresh = dropboxSyncUseCase.getLastRefresh()
        _lastRefresh.value = refresh
    }

    fun saveAccessToken() {
        viewModelScope.launch {
            val dropboxClientCredentials = Auth.getDbxCredential()
            val stringValue = dropboxClientCredentials.toString()
            dropboxSyncUseCase.saveClientCred(stringValue)
            dropboxClient.setTokenAndCreateClient(dropboxClientCredentials)
            _isDropboxConnected.value = true
        }
    }



    fun backup() {
        viewModelScope.launch {
            dropboxClient.upload()
        }
    }*/
}

package com.prof18.moneyflow.features.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dropbox.core.android.Auth
import com.dropbox.core.oauth.DbxCredential
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.prof18.moneyflow.presentation.dropboxsync.DropboxSyncUserCase
import timber.log.Timber

class DropboxLoginViewModel(
    private val dropboxSyncUserCase: DropboxSyncUserCase,
    private val dropboxClient: DropboxClient,
) : ViewModel() {

    private val _lastRefreshLiveData = MutableLiveData<String?>()
    val lastRefreshLiveData: LiveData<String?>
        get() = _lastRefreshLiveData

    private val _isDropboxConnected = MutableLiveData<Boolean>()
    val isDropboxConnected: LiveData<Boolean>
        get() = _isDropboxConnected

    init {
        viewModelScope.launch {
            dropboxClient.observeClientStatus().collect {
                Timber.d("Got Client Status com.prof18.moneyflow.data: $it")
                _isDropboxConnected.value = when (it) {
                    DropboxClientStatus.LINKED -> true
                    DropboxClientStatus.NOT_LINKED -> false
                }

            }
        }
    }

    fun getLastRefresh() {
        val refresh = dropboxSyncUserCase.getLastRefresh()
        _lastRefreshLiveData.value = refresh
    }

    fun saveAccessToken() {
        viewModelScope.launch {
            val dropboxClientCredentials = Auth.getDbxCredential()
            val stringValue = dropboxClientCredentials.toString()
            dropboxSyncUserCase.saveClientCred(stringValue)
            dropboxClient.setTokenAndCreateClient(dropboxClientCredentials)
            _isDropboxConnected.value = true
        }
    }

    fun unlinkDropbox() {

    }

    fun backup() {
        viewModelScope.launch {
            dropboxClient.upload()
        }
    }
}

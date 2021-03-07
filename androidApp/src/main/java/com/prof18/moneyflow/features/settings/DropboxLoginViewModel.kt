package com.prof18.moneyflow.features.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dropbox.core.android.Auth
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.prof18.moneyflow.presentation.dropboxsync.DropboxSyncUserCase
import timber.log.Timber

class DropboxLoginViewModel(
    private val dropboxSyncUserCase: DropboxSyncUserCase,
    private val dropboxClient: DropboxClient
) : ViewModel() {

    private val _lastRefreshLiveData = MutableLiveData<String?>()
    val lastRefreshLiveData: LiveData<String?>
        get() = _lastRefreshLiveData

    private val _isDropboxConnected = MutableLiveData<Boolean>()
    val isDropboxConnected: LiveData<Boolean>
        get() = _isDropboxConnected

    init {
        observeLastRefresh()
    }

    private fun observeLastRefresh() {

        viewModelScope.launch {
            dropboxSyncUserCase.observeLastRefresh().collect {
                Timber.d("Got last refresh: $it")
                _lastRefreshLiveData.value = it
            }
        }

        // In the same viewModel scope is not working
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

    fun saveAccessToken() {
        viewModelScope.launch {
            val dropboxToken = Auth.getOAuth2Token()
            if (dropboxToken != null) {
                dropboxSyncUserCase.saveAccessToken(dropboxToken)
                dropboxClient.setTokenAndCreateClient(dropboxToken)
                _isDropboxConnected.value = true
            }
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

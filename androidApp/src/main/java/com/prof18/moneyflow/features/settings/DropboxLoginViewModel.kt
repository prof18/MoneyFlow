package com.prof18.moneyflow.features.settings

import androidx.lifecycle.*
import com.dropbox.core.android.Auth
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent
import presentation.dropboxsync.DropboxSyncUserCase
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
                _lastRefreshLiveData.value = it
            }
        }

        // In the same viewModel scope is not working
        viewModelScope.launch {
            dropboxClient.observeClientStatus().collect {
                Timber.d("Got Client Status data: $it")
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
}

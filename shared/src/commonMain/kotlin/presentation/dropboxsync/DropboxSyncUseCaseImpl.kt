package presentation.dropboxsync

import domain.repository.DropboxSyncRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import printThrowable

class DropboxSyncUseCaseImpl(
    private val dropboxSyncRepository: DropboxSyncRepository,
    // That's only for iOs
    private val onLastRefreshUpdate: ((String?) -> Unit)? = null,
    private val onAccessTokenUpdate: ((String?) -> Unit)? = null,
) : DropboxSyncUserCase {

    // Used only on iOs
    private val coroutineScope: CoroutineScope = MainScope()

    private val lastRefresh = MutableStateFlow<String?>(null)
    override fun observeLastRefresh(): StateFlow<String?> = lastRefresh

    override fun retrieveLastRefresh() {
        coroutineScope.launch {
            getLastRefreshSuspendable()
        }
    }

    override suspend fun getLastRefreshSuspendable() {
        dropboxSyncRepository.getLastRefreshTime()
            .catch { cause ->
                printThrowable(cause)
                lastRefresh.value = null
                onLastRefreshUpdate?.invoke(null)
            }.collect {
                lastRefresh.value = it
                onLastRefreshUpdate?.invoke(it)
            }
    }

    override fun retrieveAccessToken() {
        coroutineScope.launch {
            val token = dropboxSyncRepository.getAccessToken()
            onAccessTokenUpdate?.invoke(token)
        }
    }

    override suspend fun getAccessTokenSuspendable(): String? {
        return dropboxSyncRepository.getAccessToken()
    }

    override fun saveAccessToken(token: String) {
        coroutineScope.launch {
            saveAccessTokenSuspendable(token)
        }
    }

    override suspend fun saveAccessTokenSuspendable(token: String) {
        dropboxSyncRepository.saveAccessToken(token)
    }

    override fun updateLastRefresh(lastRefreshMillis: Long) {
        coroutineScope.launch {
            updateLastRefreshSuspendable(lastRefreshMillis)
        }
    }

    override suspend fun updateLastRefreshSuspendable(lastRefreshMillis: Long) {
        dropboxSyncRepository.saveLastRefresh(lastRefreshMillis)
    }

    // iOs only
    fun onDestroy() {
        coroutineScope.cancel()
    }
}
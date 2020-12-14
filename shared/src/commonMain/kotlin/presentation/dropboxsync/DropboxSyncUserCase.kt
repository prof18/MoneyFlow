package presentation.dropboxsync

import kotlinx.coroutines.flow.StateFlow

interface DropboxSyncUserCase {
    fun observeLastRefresh(): StateFlow<String?>
    fun retrieveLastRefresh()
    suspend fun getLastRefreshSuspendable()

    fun retrieveAccessToken()
    suspend fun getAccessTokenSuspendable(): String?

    fun saveAccessToken(token: String)
    suspend fun saveAccessTokenSuspendable(token: String)

    fun updateLastRefresh(lastRefreshMillis: Long)
    suspend fun updateLastRefreshSuspendable(lastRefreshMillis: Long)
}
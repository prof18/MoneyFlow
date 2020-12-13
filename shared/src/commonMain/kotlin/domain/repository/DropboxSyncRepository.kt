package domain.repository

import kotlinx.coroutines.flow.Flow

interface DropboxSyncRepository {

    @Throws(Exception::class)
    suspend fun getAccessToken(): String?

    @Throws(Exception::class)
    suspend fun getLastRefreshTime(): Flow<String?>

    @Throws(Exception::class)
    suspend fun saveAccessToken(accessToken: String)

    @Throws(Exception::class)
    suspend fun saveLastRefresh(lastRefresh: Long)

}
package com.prof18.moneyflow.data

import com.prof18.moneyflow.db.GetLastRefresh
import com.prof18.moneyflow.data.db.DatabaseSource
import com.prof18.moneyflow.domain.repository.DropboxSyncRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import com.prof18.moneyflow.utils.Utils.formatDateAllData

class DropboxSyncRepositoryImpl(
    private val databaseSource: DatabaseSource
) : DropboxSyncRepository {

    private var lastRefresh: Flow<GetLastRefresh?> = emptyFlow()

    init {
        lastRefresh = databaseSource.getDropboxLastRefresh()
    }

    override suspend fun getAccessToken(): String? {
        return databaseSource.getDropboxAccessToken()?.accessToken
    }

    override suspend fun getLastRefreshTime(): Flow<String?> {
        return lastRefresh.map {
            it?.lastRefresh?.formatDateAllData()
            null
        }
    }

    override suspend fun saveAccessToken(accessToken: String) {
        databaseSource.insertDropboxAccessToken(accessToken)
    }

    override suspend fun saveLastRefresh(lastRefresh: Long) {
        databaseSource.updateDropboxLastRefresh(lastRefresh)
    }
}
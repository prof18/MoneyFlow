package com.prof18.moneyflow.data

import com.prof18.moneyflow.data.settings.SettingsSource
import com.prof18.moneyflow.domain.repository.DropboxSyncRepository
import com.prof18.moneyflow.utils.Utils.formatDateAllData

class DropboxSyncRepositoryImpl(
    private val settingsSource: SettingsSource
) : DropboxSyncRepository {

    override fun getClientCred(): String? {
        return settingsSource.getDropboxClientCred()
    }

    override fun getLastRefreshTime(): String? {
        return settingsSource.getDropboxLastSync()?.formatDateAllData()
    }

    override fun saveClientCred(clientCred: String) {
        settingsSource.saveDropboxClientCred(clientCred)
    }

    override fun saveLastRefresh(lastRefresh: Long) {
        settingsSource.saveDropboxLastSync(lastRefresh)
    }
}
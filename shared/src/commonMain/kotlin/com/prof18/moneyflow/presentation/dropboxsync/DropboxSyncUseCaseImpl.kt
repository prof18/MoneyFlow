package com.prof18.moneyflow.presentation.dropboxsync

import com.prof18.moneyflow.domain.repository.SettingsRepository

class DropboxSyncUseCaseImpl(
    private val settingsRepository: SettingsRepository,

) : DropboxSyncUserCase {

    override fun saveClientCred(string: String) {
        settingsRepository.saveDropboxClientCred(string)
    }

    override fun getClientCred(): String? {
        return settingsRepository.getDropboxClientCred()
    }

    override fun saveLastRefresh(lastRefreshMillis: Long) {
        settingsRepository.saveLastDropboxRefreshTime(lastRefreshMillis)
    }

    override fun getLastRefresh(): String? {
        return settingsRepository.getLastDropboxRefreshTime()
    }
}
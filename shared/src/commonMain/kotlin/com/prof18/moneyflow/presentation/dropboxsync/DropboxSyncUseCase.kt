package com.prof18.moneyflow.presentation.dropboxsync

import com.prof18.moneyflow.domain.repository.SettingsRepository

class DropboxSyncUseCase(
    private val settingsRepository: SettingsRepository,
) {

    fun saveClientCred(string: String) {
        settingsRepository.saveDropboxClientCred(string)
    }

    fun getClientCred(): String? {
        return settingsRepository.getDropboxClientCred()
    }

    fun saveLastRefresh(lastRefreshMillis: Long) {
        settingsRepository.saveLastDropboxRefreshTime(lastRefreshMillis)
    }

    fun getLastRefresh(): String? {
        return settingsRepository.getLastDropboxRefreshTime()
    }
}
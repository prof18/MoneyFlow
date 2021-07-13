package com.prof18.moneyflow.data

import com.prof18.moneyflow.data.settings.SettingsSource
import com.prof18.moneyflow.domain.repository.SettingsRepository
import com.prof18.moneyflow.utils.Utils.formatDateAllData

class SettingsRepositoryImpl(
    private val settingsSource: SettingsSource
) : SettingsRepository {

    // Just to avoid getting on disk every time the field is accessed
    private var isBiometricEnabled: Boolean? = null

    override fun getDropboxClientCred(): String? {
        return settingsSource.getDropboxClientCred()
    }

    override fun getLastDropboxRefreshTime(): String? {
        return settingsSource.getDropboxLastSync()?.formatDateAllData()
    }

    override fun saveDropboxClientCred(clientCred: String) {
        settingsSource.saveDropboxClientCred(clientCred)
    }

    override fun saveLastDropboxRefreshTime(lastRefresh: Long) {
        settingsSource.saveDropboxLastSync(lastRefresh)
    }

    override fun isBiometricEnabled(): Boolean {
        if (isBiometricEnabled == null) {
            isBiometricEnabled = settingsSource.getUseBiometric()
        }
        return isBiometricEnabled!!
    }

    override fun setBiometric(enabled: Boolean) {
        settingsSource.setUseBiometric(enabled)
        isBiometricEnabled = enabled
    }
}
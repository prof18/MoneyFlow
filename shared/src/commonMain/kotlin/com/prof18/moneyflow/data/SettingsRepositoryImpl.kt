package com.prof18.moneyflow.data

import com.prof18.moneyflow.data.settings.SettingsSource
import com.prof18.moneyflow.domain.repository.SettingsRepository
import com.prof18.moneyflow.utils.Utils.formatDateAllData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsRepositoryImpl(
    private val settingsSource: SettingsSource
) : SettingsRepository {

    // Just to avoid getting on disk every time the field is accessed
    private var isBiometricEnabled: Boolean? = null

    override val hideSensibleDataState: StateFlow<Boolean>
        get() = _hideSensibleDataState

    private var _hideSensibleDataState = MutableStateFlow(false)

    init {
        _hideSensibleDataState.value = settingsSource.getHideSensitiveData()
    }

    // TODO: expose a flow for sensitive data settings

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

    override fun setHideSensitiveData(hide: Boolean) {
        settingsSource.setHideSensitiveData(hide)
        _hideSensibleDataState.value = hide
    }
}
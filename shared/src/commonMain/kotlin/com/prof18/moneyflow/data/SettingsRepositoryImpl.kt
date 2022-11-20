package com.prof18.moneyflow.data

import com.prof18.moneyflow.data.settings.SettingsSource
import com.prof18.moneyflow.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class SettingsRepositoryImpl(
    private val settingsSource: SettingsSource,
) : SettingsRepository {

    // Just to avoid getting on disk every time the field is accessed
    private var isBiometricEnabled: Boolean? = null

    override val hideSensibleDataState: StateFlow<Boolean>
        get() = _hideSensibleDataState

    private var _hideSensibleDataState = MutableStateFlow(false)

    init {
        _hideSensibleDataState.value = settingsSource.getHideSensitiveData()
    }

    override fun getDropboxClientCred(): String? {
        return settingsSource.getDropboxClientCred()
    }

    override fun saveDropboxClientCred(clientCred: String) {
        settingsSource.saveDropboxClientCred(clientCred)
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

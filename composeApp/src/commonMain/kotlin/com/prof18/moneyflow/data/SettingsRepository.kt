package com.prof18.moneyflow.data

import com.prof18.moneyflow.data.settings.SettingsSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsRepository(
    private val settingsSource: SettingsSource,
) {

    // Just to avoid getting on disk every time the field is accessed
    private var isBiometricEnabled: Boolean? = null

    val hideSensibleDataState: StateFlow<Boolean>
        get() = _hideSensibleDataState

    private var _hideSensibleDataState = MutableStateFlow(false)

    init {
        _hideSensibleDataState.value = settingsSource.getHideSensitiveData()
    }

    fun isBiometricEnabled(): Boolean {
        if (isBiometricEnabled == null) {
            isBiometricEnabled = settingsSource.getUseBiometric()
        }
        return isBiometricEnabled!!
    }

    fun setBiometric(enabled: Boolean) {
        settingsSource.setUseBiometric(enabled)
        isBiometricEnabled = enabled
    }

    fun setHideSensitiveData(hide: Boolean) {
        settingsSource.setHideSensitiveData(hide)
        _hideSensibleDataState.value = hide
    }
}

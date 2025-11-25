package com.prof18.moneyflow.features.settings

import androidx.lifecycle.ViewModel
import com.prof18.moneyflow.data.SettingsRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private val _biometricState = MutableStateFlow(false)
    val biometricState: StateFlow<Boolean> = _biometricState

    val hideSensitiveDataState: StateFlow<Boolean> = settingsRepository.hideSensibleDataState

    init {
        _biometricState.value = settingsRepository.isBiometricEnabled()
    }

    fun updateBiometricState(enabled: Boolean) {
        settingsRepository.setBiometric(enabled)
        _biometricState.update { enabled }
    }

    fun updateHideSensitiveDataState(enabled: Boolean) {
        settingsRepository.setHideSensitiveData(enabled)
    }
}

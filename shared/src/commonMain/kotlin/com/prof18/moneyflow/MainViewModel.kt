package com.prof18.moneyflow

import androidx.lifecycle.ViewModel
import com.prof18.moneyflow.domain.repository.SettingsRepository

class MainViewModel(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    fun isBiometricEnabled(): Boolean {
        return settingsRepository.isBiometricEnabled()
    }
}

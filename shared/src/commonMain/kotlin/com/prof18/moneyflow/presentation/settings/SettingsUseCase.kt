package com.prof18.moneyflow.presentation.settings

import com.prof18.moneyflow.domain.repository.SettingsRepository

class SettingsUseCase(
    private val settingsRepository: SettingsRepository
) {

    fun isBiometricEnabled(): Boolean {
        return settingsRepository.isBiometricEnabled()
    }

    fun toggleBiometricStatus(status: Boolean) {
        settingsRepository.setBiometric(status)
    }

}
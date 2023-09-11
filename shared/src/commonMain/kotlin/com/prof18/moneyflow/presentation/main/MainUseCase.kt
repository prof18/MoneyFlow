package com.prof18.moneyflow.presentation.main

import com.prof18.moneyflow.domain.repository.SettingsRepository
import kotlin.native.HiddenFromObjC

@HiddenFromObjC
class MainUseCase(
    private val settingsRepository: SettingsRepository,
) {

    fun isBiometricEnabled(): Boolean {
        return settingsRepository.isBiometricEnabled()
    }

    fun toggleBiometricStatus(status: Boolean) {
        settingsRepository.setBiometric(status)
    }
}

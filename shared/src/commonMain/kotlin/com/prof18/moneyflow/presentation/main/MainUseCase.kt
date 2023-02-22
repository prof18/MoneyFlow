package com.prof18.moneyflow.presentation.main

import com.prof18.moneyflow.domain.repository.SettingsRepository
import kotlin.native.HiddenFromObjC
import kotlin.native.ObjCName

@ObjCName("_MainUseCase")
class MainUseCase(
    private val settingsRepository: SettingsRepository,
) {

    @HiddenFromObjC
    fun isBiometricEnabled(): Boolean {
        return settingsRepository.isBiometricEnabled()
    }

    @HiddenFromObjC
    fun toggleBiometricStatus(status: Boolean) {
        settingsRepository.setBiometric(status)
    }
}

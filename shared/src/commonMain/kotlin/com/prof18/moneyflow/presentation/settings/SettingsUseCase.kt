package com.prof18.moneyflow.presentation.settings

import com.prof18.moneyflow.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.StateFlow
import kotlin.native.HiddenFromObjC

@HiddenFromObjC
class SettingsUseCase(
    private val settingsRepository: SettingsRepository,
) {

    val sensitiveDataVisibilityState: StateFlow<Boolean> = settingsRepository.hideSensibleDataState

    fun isBiometricEnabled(): Boolean {
        return settingsRepository.isBiometricEnabled()
    }

    fun toggleBiometricStatus(status: Boolean) {
        settingsRepository.setBiometric(status)
    }

    fun toggleHideSensitiveData(status: Boolean) {
        settingsRepository.setHideSensitiveData(status)
    }
}

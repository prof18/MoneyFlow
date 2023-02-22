package com.prof18.moneyflow.presentation.settings

import com.prof18.moneyflow.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.StateFlow
import kotlin.native.HiddenFromObjC
import kotlin.native.ObjCName

@ObjCName("_SettingsUseCase")
class SettingsUseCase(
    private val settingsRepository: SettingsRepository,
) {

    @HiddenFromObjC
    val sensitiveDataVisibilityState: StateFlow<Boolean> = settingsRepository.hideSensibleDataState

    @HiddenFromObjC
    fun isBiometricEnabled(): Boolean {
        return settingsRepository.isBiometricEnabled()
    }

    @HiddenFromObjC
    fun toggleBiometricStatus(status: Boolean) {
        settingsRepository.setBiometric(status)
    }

    @HiddenFromObjC
    fun toggleHideSensitiveData(status: Boolean) {
        settingsRepository.setHideSensitiveData(status)
    }
}

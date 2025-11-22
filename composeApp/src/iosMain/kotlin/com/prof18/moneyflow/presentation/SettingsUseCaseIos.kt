package com.prof18.moneyflow.presentation

import com.prof18.moneyflow.FlowWrapper
import com.prof18.moneyflow.presentation.settings.SettingsUseCase

@ObjCName("SettingsUseCase")
class SettingsUseCaseIos(
    private val settingsUseCase: SettingsUseCase,
) : BaseUseCaseIos() {

    val getSensitiveDataVisibilityState: FlowWrapper<Boolean> =
        FlowWrapper(scope, settingsUseCase.sensitiveDataVisibilityState)

    fun isBiometricEnabled(): Boolean = settingsUseCase.isBiometricEnabled()

    fun toggleBiometricStatus(status: Boolean) = settingsUseCase.toggleBiometricStatus(status)

    fun toggleHideSensitiveData(status: Boolean) = settingsUseCase.toggleHideSensitiveData(status)
}

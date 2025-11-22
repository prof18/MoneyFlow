package com.prof18.moneyflow.features.settings

import androidx.lifecycle.ViewModel
import com.prof18.moneyflow.presentation.settings.SettingsUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel(
    private val settingsUseCase: SettingsUseCase,
    private val backupManager: BackupManager,
) : ViewModel() {

    private val _biometricState = MutableStateFlow(false)
    val biometricState: StateFlow<Boolean> = _biometricState

    val hideSensitiveDataState: StateFlow<Boolean> = settingsUseCase.sensitiveDataVisibilityState

    init {
        _biometricState.value = settingsUseCase.isBiometricEnabled()
    }

    fun performBackup(request: BackupRequest) {
        backupManager.performBackup(request)
    }

    fun performRestore(request: BackupRequest) {
        backupManager.performRestore(request)
    }

    fun updateBiometricState(enabled: Boolean) {
        settingsUseCase.toggleBiometricStatus(enabled)
        _biometricState.update { enabled }
    }

    fun updateHideSensitiveDataState(enabled: Boolean) {
        settingsUseCase.toggleHideSensitiveData(enabled)
    }
}

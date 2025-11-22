package com.prof18.moneyflow.features.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.prof18.moneyflow.presentation.settings.SettingsUseCase
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel(
    private val settingsUseCase: SettingsUseCase,
    private val backupManager: BackupManager,
) : ViewModel() {

    var biometricState: Boolean by mutableStateOf(false)
        private set

    val hideSensitiveDataState: StateFlow<Boolean> = settingsUseCase.sensitiveDataVisibilityState

    init {
        biometricState = settingsUseCase.isBiometricEnabled()
    }

    fun performBackup(request: BackupRequest) {
        backupManager.performBackup(request)
    }

    fun performRestore(request: BackupRequest) {
        backupManager.performRestore(request)
    }

    fun updateBiometricState(enabled: Boolean) {
        settingsUseCase.toggleBiometricStatus(enabled)
        biometricState = enabled
    }

    fun updateHideSensitiveDataState(enabled: Boolean) {
        settingsUseCase.toggleHideSensitiveData(enabled)
    }
}

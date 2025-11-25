package com.prof18.moneyflow.features.settings

import androidx.lifecycle.ViewModel
import com.prof18.moneyflow.data.SettingsRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    private val backupManager: BackupManager,
) : ViewModel() {

    private val _biometricState = MutableStateFlow(false)
    val biometricState: StateFlow<Boolean> = _biometricState

    val hideSensitiveDataState: StateFlow<Boolean> = settingsRepository.hideSensibleDataState

    init {
        _biometricState.value = settingsRepository.isBiometricEnabled()
    }

    fun performBackup(request: BackupRequest) {
        backupManager.performBackup(request)
    }

    fun performRestore(request: BackupRequest) {
        backupManager.performRestore(request)
    }

    fun updateBiometricState(enabled: Boolean) {
        settingsRepository.setBiometric(enabled)
        _biometricState.update { enabled }
    }

    fun updateHideSensitiveDataState(enabled: Boolean) {
        settingsRepository.setHideSensitiveData(enabled)
    }
}

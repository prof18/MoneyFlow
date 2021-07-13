package com.prof18.moneyflow.features.settings

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.prof18.moneyflow.presentation.settings.SettingsUseCase
import com.prof18.moneyflow.utils.DatabaseImportExport

class SettingsViewModel(
    private val databaseImportExport: DatabaseImportExport,
    private val settingsUseCase: SettingsUseCase
) : ViewModel() {

    var biometricState: Boolean by mutableStateOf(false)
        private set

    init {
        biometricState = settingsUseCase.isBiometricEnabled()
    }

    fun performBackup(uri: Uri) {
        databaseImportExport.exportToMemory(uri)
    }

    fun performRestore(uri: Uri) {
        databaseImportExport.importFromMemory(uri)
    }

    fun updateBiometricState(enabled: Boolean) {
        settingsUseCase.toggleBiometricStatus(enabled)
        biometricState = enabled
    }


}
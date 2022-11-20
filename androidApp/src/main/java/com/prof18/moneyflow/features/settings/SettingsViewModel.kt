package com.prof18.moneyflow.features.settings

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.prof18.moneyflow.database.DBImportExport
import com.prof18.moneyflow.presentation.settings.SettingsUseCase
import kotlinx.coroutines.flow.StateFlow

internal class SettingsViewModel(
    private val databaseImportExport: DBImportExport,
    private val settingsUseCase: SettingsUseCase,
) : ViewModel() {

    var biometricState: Boolean by mutableStateOf(false)
        private set

    val hideSensitiveDataState: StateFlow<Boolean> = settingsUseCase.sensitiveDataVisibilityState

    init {
        biometricState = settingsUseCase.isBiometricEnabled()
    }

    fun performBackup(uri: Uri) {
        // TODO: handle error message
        databaseImportExport.exportDatabaseToFileSystem(uri)
    }

    fun performRestore(uri: Uri) {
        // TODO: handle error message
        databaseImportExport.importDatabaseFromFileSystem(uri)
    }

    fun updateBiometricState(enabled: Boolean) {
        settingsUseCase.toggleBiometricStatus(enabled)
        biometricState = enabled
    }

    fun updateHideSensitiveDataState(enabled: Boolean) {
        settingsUseCase.toggleHideSensitiveData(enabled)
    }
}

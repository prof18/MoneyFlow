package com.prof18.moneyflow.features.settings

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prof18.moneyflow.utils.DatabaseImportExport
import org.koin.java.KoinJavaComponent.getKoin

class SettingsViewModel(
    private var databaseImportExport: DatabaseImportExport,
) : ViewModel() {

    fun performBackup(uri: Uri) {
        databaseImportExport.export(uri)
    }

    fun performRestore(uri: Uri) {
        databaseImportExport.import(uri)
    }
}

class SettingsViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(getKoin().get()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
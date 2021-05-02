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
        databaseImportExport.exportToMemory(uri)
    }

    fun performRestore(uri: Uri) {
        databaseImportExport.importFromMemory(uri)
    }
}
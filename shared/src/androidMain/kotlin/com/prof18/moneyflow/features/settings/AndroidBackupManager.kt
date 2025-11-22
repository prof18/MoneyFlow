package com.prof18.moneyflow.features.settings

import android.net.Uri
import com.prof18.moneyflow.database.DBImportExport

actual class BackupRequest(val uri: Uri)

class AndroidBackupManager(
    private val databaseImportExport: DBImportExport,
) : BackupManager {
    override fun performBackup(request: BackupRequest) {
        databaseImportExport.exportDatabaseToFileSystem(request.uri)
    }

    override fun performRestore(request: BackupRequest) {
        databaseImportExport.importDatabaseFromFileSystem(request.uri)
    }
}

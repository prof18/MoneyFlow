package com.prof18.moneyflow.features.settings

import android.net.Uri
import com.prof18.moneyflow.database.DBImportExport

actual class BackupRequest(val uri: Uri)

actual class BackupManager actual constructor(
    private val databaseImportExport: DBImportExport,
) {
    actual fun performBackup(request: BackupRequest) {
        databaseImportExport.exportDatabaseToFileSystem(request.uri)
    }

    actual fun performRestore(request: BackupRequest) {
        databaseImportExport.importDatabaseFromFileSystem(request.uri)
    }
}

package com.prof18.moneyflow.features.settings

import com.prof18.moneyflow.database.DBImportExport

actual class BackupRequest

actual class BackupManager actual constructor(
    private val databaseImportExport: DBImportExport,
) {
    actual fun performBackup(request: BackupRequest) {
        // No-op for now
    }

    actual fun performRestore(request: BackupRequest) {
        // No-op for now
    }
}

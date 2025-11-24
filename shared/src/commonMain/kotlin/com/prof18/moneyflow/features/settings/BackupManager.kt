package com.prof18.moneyflow.features.settings

import com.prof18.moneyflow.database.DBImportExport

expect class BackupRequest

expect class BackupManager(databaseImportExport: DBImportExport) {
    fun performBackup(request: BackupRequest)
    fun performRestore(request: BackupRequest)
}

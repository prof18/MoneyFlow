package com.prof18.moneyflow.features.settings

expect class BackupRequest

interface BackupManager {
    fun performBackup(request: BackupRequest)
    fun performRestore(request: BackupRequest)
}

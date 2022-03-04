package com.prof18.moneyflow.database

import android.net.Uri
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import java.io.File

interface DBImportExport {
    fun generateDatabaseFile(): File?
    fun exportDatabaseToFileSystem(uri: Uri): MoneyFlowResult<Unit>
    fun importDatabaseFromFileSystem(uri: Uri): MoneyFlowResult<Unit>
    fun databasePath(): String
}

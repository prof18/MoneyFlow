package com.prof18.moneyflow.database

import com.prof18.moneyflow.domain.entities.MoneyFlowResult

expect class DBImportExport {
    fun generateDatabaseFile(): Any?
    fun exportDatabaseToFileSystem(uri: Any): MoneyFlowResult<Unit>
    fun importDatabaseFromFileSystem(uri: Any): MoneyFlowResult<Unit>
    fun databasePath(): String
}

package com.prof18.moneyflow.database

import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneyFlowResult

actual class DBImportExport {
    actual fun generateDatabaseFile(): Any? = null

    actual fun exportDatabaseToFileSystem(uri: Any): MoneyFlowResult<Unit> =
        MoneyFlowResult.Error(MoneyFlowError.DatabaseExport(Exception("Not implemented")))

    actual fun importDatabaseFromFileSystem(uri: Any): MoneyFlowResult<Unit> =
        MoneyFlowResult.Error(MoneyFlowError.DatabaseImport(Exception("Not implemented")))

    actual fun databasePath(): String = ""
}

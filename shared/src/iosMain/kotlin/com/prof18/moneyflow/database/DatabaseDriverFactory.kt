package com.prof18.moneyflow.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.prof18.moneyflow.db.MoneyFlowDB

internal fun createDatabaseDriver(useDebugDatabaseName: Boolean = false): SqlDriver {
    return NativeSqliteDriver(
        schema = MoneyFlowDB.Schema,
        name = if (useDebugDatabaseName) {
            DatabaseHelper.APP_DATABASE_NAME_DEBUG
        } else {
            DatabaseHelper.APP_DATABASE_NAME_PROD
        },
    )
}

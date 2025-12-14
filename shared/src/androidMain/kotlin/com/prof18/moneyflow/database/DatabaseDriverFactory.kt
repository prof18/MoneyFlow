package com.prof18.moneyflow.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.prof18.moneyflow.db.MoneyFlowDB

internal fun createDatabaseDriver(context: Context, useDebugDatabaseName: Boolean = false): SqlDriver {
    return AndroidSqliteDriver(
        schema = MoneyFlowDB.Schema,
        context = context,
        name = if (useDebugDatabaseName) {
            DatabaseHelper.APP_DATABASE_NAME_DEBUG
        } else {
            DatabaseHelper.APP_DATABASE_NAME_PROD
        },
    )
}

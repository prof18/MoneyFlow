package com.prof18.moneyflow.utilities

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.prof18.moneyflow.database.DatabaseHelper
import com.prof18.moneyflow.db.MoneyFlowDB

internal actual fun createDriver() {
    val nativeDriver = NativeSqliteDriver(MoneyFlowDB.Schema, name = "moneydb.db")
    databaseHelper = DatabaseHelper(nativeDriver)
    driver = nativeDriver
}

internal actual fun closeDriver() {
    driver?.close()
    driver = null
    databaseHelper = null
}

internal actual fun getDatabaseHelper(): DatabaseHelper = requireNotNull(databaseHelper)

private var driver: SqlDriver? = null
private var databaseHelper: DatabaseHelper? = null

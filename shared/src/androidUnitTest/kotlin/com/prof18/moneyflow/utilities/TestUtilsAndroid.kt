package com.prof18.moneyflow.utilities

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.prof18.moneyflow.database.DatabaseHelper
import com.prof18.moneyflow.db.MoneyFlowDB
import kotlinx.coroutines.Dispatchers

actual fun createDriver() {
    val jdbcDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    MoneyFlowDB.Schema.create(jdbcDriver)
    databaseHelper = DatabaseHelper(jdbcDriver, Dispatchers.Unconfined)
    driver = jdbcDriver
}

actual fun closeDriver() {
    driver?.close()
    databaseHelper = null
    driver = null
}

actual fun getDatabaseHelper(): DatabaseHelper = requireNotNull(databaseHelper)

private var driver: app.cash.sqldelight.db.SqlDriver? = null
private var databaseHelper: DatabaseHelper? = null

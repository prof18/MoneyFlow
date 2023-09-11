package com.prof18.moneyflow.utilities

import com.prof18.moneyflow.data.db.Schema
import com.prof18.moneyflow.database.DatabaseHelper
import com.prof18.moneyflow.db.MoneyFlowDB
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

actual fun createDriver() {
    val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    Schema.create(driver)
    DatabaseHelper.setupDatabase(driver)
}

actual fun closeDriver() {
    DatabaseHelper.dbClear()
}

actual fun getDb(): MoneyFlowDB {
    return DatabaseHelper.instance
}

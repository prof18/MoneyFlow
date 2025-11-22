package com.prof18.moneyflow.utilities

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import app.cash.sqldelight.driver.native.wrapConnection
import co.touchlab.sqliter.DatabaseConfiguration
import com.prof18.moneyflow.data.db.Schema
import com.prof18.moneyflow.database.DatabaseHelper
import com.prof18.moneyflow.db.MoneyFlowDB

actual fun createDriver() {
    val driver = NativeSqliteDriver(
        DatabaseConfiguration(
            name = "moneydb.db",
            version = 1,
            inMemory = true,
            create = { connection ->
                wrapConnection(connection) { Schema.create(it) }
            },
            upgrade = { connection, oldVersion, newVersion ->
                wrapConnection(connection) { MoneyFlowDB.Schema.migrate(it, oldVersion, newVersion) }
            },
        ),
    )
    DatabaseHelper.setupDatabase(driver)
}

actual fun closeDriver() {
    DatabaseHelper.dbClear()
}

actual fun getDb(): MoneyFlowDB {
    return DatabaseHelper.instance
}

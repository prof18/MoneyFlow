package com.prof18.moneyflow.utilities

import com.prof18.moneyflow.db.MoneyFlowDB
import com.prof18.moneyflow.database.DatabaseHelper
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.squareup.sqldelight.drivers.native.wrapConnection
import co.touchlab.sqliter.DatabaseConfiguration


actual fun createDriver() {
    val driver = NativeSqliteDriver(
        DatabaseConfiguration(
            name = "moneydb.db",
            version = 1,
            inMemory = true,
            create = { connection ->
                wrapConnection(connection) { MoneyFlowDB.Schema.create(it) }
            },
            upgrade = { connection, oldVersion, newVersion ->
                wrapConnection(connection) { MoneyFlowDB.Schema.migrate(it, oldVersion, newVersion) }
            }
        )
    )
    DatabaseHelper.setupDatabase(driver)
//    val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
//    Schema.create(driver)
//    DatabaseHelper.setupDatabase(driver)
}

actual fun closeDriver() {
    DatabaseHelper.dbClear()
}

actual fun getDb(): MoneyFlowDB {
    return DatabaseHelper.instance
}
package com.prof18.moneyflow.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.prof18.moneyflow.data.db.Schema
import com.prof18.moneyflow.data.db.createQueryWrapper
import com.prof18.moneyflow.db.MoneyFlowDB
import kotlin.concurrent.AtomicReference

object DatabaseHelper {
    private val driverRef = AtomicReference<SqlDriver?>(null)
    private val dbRef = AtomicReference<MoneyFlowDB?>(null)

    fun setupDatabase(driver: SqlDriver? = null) {
        val databaseDriver = driver ?: NativeSqliteDriver(Schema, "MoneyFlowDB")
        val db = createQueryWrapper(databaseDriver)
        driverRef.value = databaseDriver
        dbRef.value = db
    }

    fun dbClear() {
        driverRef.value?.close()
        dbRef.value = null
        driverRef.value = null
    }

    val instance: MoneyFlowDB
        get() = dbRef.value!!
}

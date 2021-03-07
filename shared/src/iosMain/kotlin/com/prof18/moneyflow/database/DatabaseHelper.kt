package com.prof18.moneyflow.database

import co.touchlab.stately.freeze
import com.prof18.moneyflow.db.MoneyFlowDB
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.prof18.moneyflow.data.db.Schema
import com.prof18.moneyflow.data.db.createQueryWrapper
import kotlin.native.concurrent.AtomicReference


object DatabaseHelper {
    private val driverRef = AtomicReference<SqlDriver?>(null)
    private val dbRef = AtomicReference<MoneyFlowDB?>(null)
    private val loggingEnabled = AtomicReference(true)

    fun setupDatabase(driver: SqlDriver? = null ) {
        val databaseDriver = driver ?: NativeSqliteDriver(Schema, "MoneyFlowDB")
        val db = createQueryWrapper(databaseDriver)
        driverRef.value = databaseDriver.freeze()
        dbRef.value = db.freeze()
    }

    fun dbClear() {
        driverRef.value!!.close()
        dbRef.value = null
        driverRef.value = null
    }

    val instance: MoneyFlowDB
        get() = dbRef.value!!
}
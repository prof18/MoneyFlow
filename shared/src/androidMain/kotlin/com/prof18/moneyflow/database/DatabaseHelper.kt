package com.prof18.moneyflow.database

import com.prof18.moneyflow.db.MoneyFlowDB
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.prof18.moneyflow.data.db.Schema
import com.prof18.moneyflow.data.db.createQueryWrapper
import org.koin.java.KoinJavaComponent.getKoin

object DatabaseHelper {

    private var driverRef: SqlDriver? = null
    private var dbRef: MoneyFlowDB? = null
    const val DATABASE_NAME = "MoneyFlowDB"

    fun setupDatabase(driver: SqlDriver? = null ) {
        val driverRef = driver ?: AndroidSqliteDriver(
            Schema,
            getKoin().get(),
            DATABASE_NAME
        )
        this.driverRef = driverRef
        dbRef = createQueryWrapper(driverRef)
    }

     fun dbClear() {
        driverRef!!.close()
        dbRef = null
        driverRef = null
    }

    val instance: MoneyFlowDB
        get() = dbRef!!

}
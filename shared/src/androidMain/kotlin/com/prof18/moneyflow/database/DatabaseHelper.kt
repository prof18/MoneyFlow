package com.prof18.moneyflow.database

import com.prof18.moneyflow.data.db.DATABASE_NAME
import com.prof18.moneyflow.data.db.Schema
import com.prof18.moneyflow.data.db.createQueryWrapper
import com.prof18.moneyflow.db.MoneyFlowDB
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.java.KoinJavaComponent.getKoin

internal object DatabaseHelper {

    private var driverRef: SqlDriver? = null
    private var dbRef: MoneyFlowDB? = null

    fun setupDatabase(driver: SqlDriver? = null) {
        val driverRef = driver ?: AndroidSqliteDriver(
            Schema,
            getKoin().get(),
            DATABASE_NAME,
        )
        this.driverRef = driverRef
        dbRef = createQueryWrapper(driverRef)
    }

    fun dbClear() {
        driverRef?.close()
        dbRef = null
        driverRef = null
    }

    val instance: MoneyFlowDB
        get() = dbRef!!
}

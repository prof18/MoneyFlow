package com.prof18.moneyflow.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.prof18.moneyflow.data.db.DATABASE_NAME
import com.prof18.moneyflow.data.db.Schema
import com.prof18.moneyflow.data.db.createQueryWrapper
import com.prof18.moneyflow.db.MoneyFlowDB
import org.koin.java.KoinJavaComponent.getKoin

internal object DatabaseHelper {

    private var driverRef: SqlDriver? = null
    private var dbRef: MoneyFlowDB? = null

    fun setupDatabase(driver: SqlDriver? = null) {
        val driverRef = driver ?: AndroidSqliteDriver(
            schema = Schema,
            context = getKoin().get(),
            name = DATABASE_NAME,
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

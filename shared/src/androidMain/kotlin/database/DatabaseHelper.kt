package database

import com.prof18.moneyflow.db.MoneyFlowDB
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import data.db.Schema
import data.db.createQueryWrapper
import org.koin.java.KoinJavaComponent.getKoin

object DatabaseHelper {

    private var driverRef: SqlDriver? = null
    private var dbRef: MoneyFlowDB? = null

    fun setupDatabase(driver: SqlDriver? = null ) {
        val driverRef = driver ?: AndroidSqliteDriver(
            Schema,
            getKoin().get(),
            "MoneyFlowDB"
        )
        this.driverRef = driverRef
        dbRef = createQueryWrapper(driverRef)
    }

    internal fun dbClear() {
        driverRef!!.close()
        dbRef = null
        driverRef = null
    }

    val instance: MoneyFlowDB
        get() = dbRef!!

}
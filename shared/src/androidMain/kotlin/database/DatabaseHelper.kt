package database

import android.content.Context
import com.prof18.moneyflow.db.MoneyFlowDB
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import data.db.createQueryWrapper
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.getKoin

object DatabaseHelper {

    private var driverRef: SqlDriver? = null
    private var dbRef: MoneyFlowDB? = null

    fun setupDatabase() {
        val driverRef = AndroidSqliteDriver(
            MoneyFlowDB.Schema,
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
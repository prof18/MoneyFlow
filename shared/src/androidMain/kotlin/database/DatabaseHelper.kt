package database

import com.prof18.moneyflow.db.MoneyFlowDB
import com.squareup.sqldelight.db.SqlDriver
import data.db.createQueryWrapper

object DatabaseHelper {

    private var driverRef: SqlDriver? = null
    private var dbRef: MoneyFlowDB? = null

    fun dbSetup(driver: SqlDriver) {
        val db = createQueryWrapper(driver)
        driverRef = driver
        dbRef = db
    }

    internal fun dbClear() {
        driverRef!!.close()
        dbRef = null
        driverRef = null
    }

    val instance: MoneyFlowDB
        get() = dbRef!!


}
package data.db

import com.prof18.moneyflow.db.MoneyFlowDB
import com.squareup.sqldelight.db.SqlDriver

fun createQueryWrapper(driver: SqlDriver): MoneyFlowDB {
    return MoneyFlowDB(
        driver
    )
}

//expect fun getSQLDriver(): SqlDriver
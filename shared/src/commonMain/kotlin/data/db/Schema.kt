package data.db

import com.prof18.moneyflow.db.*
import com.squareup.sqldelight.EnumColumnAdapter
import com.squareup.sqldelight.db.SqlDriver

fun createQueryWrapper(driver: SqlDriver): MoneyFlowDB {
    return MoneyFlowDB(
        driver,
        CategoryTableAdapter = CategoryTable.Adapter(
            typeAdapter = EnumColumnAdapter()
        ),
        TransactionTableAdapter = TransactionTable.Adapter(
            typeAdapter = EnumColumnAdapter()
        ),
        AccountTableAdapter = AccountTable.Adapter(
            currencyAdapter = EnumColumnAdapter()
        )
    )
}

//expect fun getSQLDriver(): SqlDriver
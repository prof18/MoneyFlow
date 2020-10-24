package data.db

import com.prof18.moneyflow.db.Account
import com.prof18.moneyflow.db.Category
import com.prof18.moneyflow.db.MoneyFlowDB
import com.prof18.moneyflow.db.Transactions
import com.squareup.sqldelight.EnumColumnAdapter
import com.squareup.sqldelight.db.SqlDriver

fun createQueryWrapper(driver: SqlDriver): MoneyFlowDB {
    return MoneyFlowDB(
        driver,
        CategoryAdapter = Category.Adapter(
            typeAdapter = EnumColumnAdapter()
        ),
        TransactionsAdapter = Transactions.Adapter(
            typeAdapter = EnumColumnAdapter()
        ),
        AccountAdapter = Account.Adapter(
            currencyAdapter = EnumColumnAdapter()
        )
    )
}

//expect fun getSQLDriver(): SqlDriver
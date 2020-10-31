package data.db

import com.prof18.moneyflow.db.AccountTable
import com.prof18.moneyflow.db.CategoryTable
import com.prof18.moneyflow.db.MoneyFlowDB
import com.prof18.moneyflow.db.TransactionTable
import com.squareup.sqldelight.EnumColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import data.db.default.defaultCategories

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

object Schema : SqlDriver.Schema by MoneyFlowDB.Schema {
    override fun create(driver: SqlDriver) {
        MoneyFlowDB.Schema.create(driver)

        // Seed data time!
        createQueryWrapper(driver).apply {
            for (category in defaultCategories) {
                categoryTableQueries.insertCategory(
                    name = category.name,
                    type = category.type,
                    iconName = category.iconName
                )
            }
        }

    }
}
//expect fun getSQLDriver(): SqlDriver
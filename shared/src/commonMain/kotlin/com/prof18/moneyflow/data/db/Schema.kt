package com.prof18.moneyflow.data.db

import com.prof18.moneyflow.db.AccountTable
import com.prof18.moneyflow.db.CategoryTable
import com.prof18.moneyflow.db.MoneyFlowDB
import com.prof18.moneyflow.db.TransactionTable
import com.squareup.sqldelight.EnumColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import com.prof18.moneyflow.data.db.default.defaultCategories
import com.prof18.moneyflow.data.db.model.Currency

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

        // Seed com.prof18.moneyflow.data time!
        createQueryWrapper(driver).apply {

            accountTableQueries.insertAccount(
                name = "Default Account",
                currency = Currency.EURO,
                amount = 0.0
            )

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
package com.prof18.moneyflow.data.db

import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.db.AfterVersion
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import com.prof18.moneyflow.data.db.default.defaultCategories
import com.prof18.moneyflow.data.db.model.Currency
import com.prof18.moneyflow.db.AccountTable
import com.prof18.moneyflow.db.CategoryTable
import com.prof18.moneyflow.db.MoneyFlowDB
import com.prof18.moneyflow.db.TransactionTable

const val DB_FILE_NAME_WITH_EXTENSION = "MoneyFlow.db"
const val DB_FILE_NAME = "MoneyFlow"
const val DATABASE_NAME = "MoneyFlowDB"

internal fun createQueryWrapper(driver: SqlDriver): MoneyFlowDB {
    return MoneyFlowDB(
        driver,
        CategoryTableAdapter = CategoryTable.Adapter(
            typeAdapter = EnumColumnAdapter(),
        ),
        TransactionTableAdapter = TransactionTable.Adapter(
            typeAdapter = EnumColumnAdapter(),
        ),
        AccountTableAdapter = AccountTable.Adapter(
            currencyAdapter = EnumColumnAdapter(),
        ),
    )
}

internal object Schema : SqlSchema<QueryResult.Value<Unit>> {
    override val version: Long
        get() = MoneyFlowDB.Schema.version

    override fun create(driver: SqlDriver): QueryResult.Value<Unit> {
        val result = MoneyFlowDB.Schema.create(driver)
        val queryWrapper = createQueryWrapper(driver)

        queryWrapper.accountTableQueries.insertAccount(
            name = "Default Account",
            currency = Currency.EURO,
            amount = 0.0,
        )

        for (category in defaultCategories) {
            queryWrapper.categoryTableQueries.insertCategory(
                name = category.name,
                type = category.type,
                iconName = category.iconName,
            )
        }

        return result
    }

    override fun migrate(
        driver: SqlDriver,
        oldVersion: Long,
        newVersion: Long,
        vararg callbacks: AfterVersion,
    ): QueryResult.Value<Unit> = MoneyFlowDB.Schema.migrate(driver, oldVersion, newVersion, *callbacks)
}

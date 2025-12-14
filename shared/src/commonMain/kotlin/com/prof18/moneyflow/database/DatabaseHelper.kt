package com.prof18.moneyflow.database

import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.Transacter
import app.cash.sqldelight.TransactionWithoutReturn
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrDefault
import app.cash.sqldelight.db.SqlDriver
import com.prof18.moneyflow.database.default.defaultCategories
import com.prof18.moneyflow.database.model.TransactionType
import com.prof18.moneyflow.db.AccountTable
import com.prof18.moneyflow.db.CategoryTable
import com.prof18.moneyflow.db.MoneyFlowDB
import com.prof18.moneyflow.db.SelectLatestTransactions
import com.prof18.moneyflow.db.SelectMonthlyRecap
import com.prof18.moneyflow.db.SelectTransactionsPaginated
import com.prof18.moneyflow.db.TransactionTable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.time.Clock

internal class DatabaseHelper(
    sqlDriver: SqlDriver,
    dispatcher: CoroutineDispatcher? = null,
) {
    private val backgroundDispatcher: CoroutineDispatcher = dispatcher ?: Dispatchers.Default

    private val dbRef: MoneyFlowDB = MoneyFlowDB(
        driver = sqlDriver,
        CategoryTableAdapter = CategoryTable.Adapter(
            typeAdapter = EnumColumnAdapter(),
        ),
        TransactionTableAdapter = TransactionTable.Adapter(
            typeAdapter = EnumColumnAdapter(),
        ),
    )

    init {
        seedDefaultsIfNeeded()
    }

    fun selectLatestTransactions(
        accountId: Long,
        limit: Long,
    ): Flow<List<SelectLatestTransactions>> =
        dbRef.transactionTableQueries
            .selectLatestTransactions(
                accountId = accountId,
                limit = limit,
            )
            .asFlow()
            .mapToList(backgroundDispatcher)
            .flowOn(backgroundDispatcher)

    fun selectAllCategories(): Flow<List<CategoryTable>> =
        dbRef.categoryTableQueries
            .selectAll()
            .asFlow()
            .mapToList(backgroundDispatcher)
            .flowOn(backgroundDispatcher)

    fun selectCategoriesByType(type: TransactionType): Flow<List<CategoryTable>> =
        dbRef.categoryTableQueries
            .selectByType(type)
            .asFlow()
            .mapToList(backgroundDispatcher)
            .flowOn(backgroundDispatcher)

    fun selectDefaultAccount(): Flow<AccountTable> =
        dbRef.accountTableQueries
            .selectDefaultAccount()
            .asFlow()
            .mapToOneOrDefault(
                AccountTable(
                    id = 1.toLong(),
                    name = "Default Account",
                    currencyCode = "EUR",
                    currencySymbol = "€",
                    currencyDecimalPlaces = 2.toLong(),
                    isDefault = 1.toLong(),
                    createdAtMillis = Clock.System.now().toEpochMilliseconds(),
                ),
                backgroundDispatcher,
            )
            .flowOn(backgroundDispatcher)

    fun selectAccountBalance(accountId: Long): Flow<Long> =
        dbRef.transactionTableQueries
            .selectAccountBalance(accountId)
            .asFlow()
            .mapToOneOrDefault(0L, backgroundDispatcher)
            .flowOn(backgroundDispatcher)

    fun selectMonthlyRecap(
        accountId: Long,
        monthStartMillis: Long,
        monthEndMillis: Long,
    ): Flow<SelectMonthlyRecap> =
        dbRef.transactionTableQueries
            .selectMonthlyRecap(accountId, monthStartMillis, monthEndMillis)
            .asFlow()
            .mapToOneOrDefault(
                SelectMonthlyRecap(
                    incomeCents = 0L,
                    outcomeCents = 0L,
                ),
                backgroundDispatcher,
            )
            .flowOn(backgroundDispatcher)

    suspend fun insertTransaction(
        accountId: Long,
        dateMillis: Long,
        amountCents: Long,
        description: String?,
        categoryId: Long,
        transactionType: TransactionType,
    ) {
        val createdAtMillis = Clock.System.now().toEpochMilliseconds()
        dbRef.transactionWithContext(backgroundDispatcher) {
            dbRef.transactionTableQueries.insertTransaction(
                accountId = accountId,
                dateMillis = dateMillis,
                amountCents = amountCents,
                description = description,
                categoryId = categoryId,
                type = transactionType,
                createdAtMillis = createdAtMillis,
            )
        }
    }

    suspend fun deleteTransaction(
        transactionId: Long,
    ) {
        dbRef.transactionWithContext(backgroundDispatcher) {
            dbRef.transactionTableQueries.deleteTransaction(transactionId)
        }
    }

    suspend fun getTransaction(transactionId: Long): TransactionTable? =
        withContext(backgroundDispatcher) {
            return@withContext dbRef.transactionTableQueries.selectTransaction(transactionId)
                .executeAsOneOrNull()
        }

    suspend fun getTransactionsPaginated(
        accountId: Long,
        pageNum: Long,
        pageSize: Long,
    ): List<SelectTransactionsPaginated> = withContext(backgroundDispatcher) {
        val offset = pageNum * pageSize
        return@withContext dbRef.transactionTableQueries
            .selectTransactionsPaginated(
                accountId = accountId,
                pageSize = pageSize,
                offset = offset,
            )
            .executeAsList()
    }

    suspend fun insertCategory(
        name: String,
        type: TransactionType,
        iconName: String,
        isSystem: Boolean,
        createdAtMillis: Long = Clock.System.now().toEpochMilliseconds(),
    ) = withContext(backgroundDispatcher) {
        dbRef.categoryTableQueries.insertCategory(
            name = name,
            type = type,
            iconName = iconName,
            isSystem = if (isSystem) 1L else 0L,
            createdAtMillis = createdAtMillis,
        )
    }

    suspend fun updateCategory(
        id: Long,
        name: String,
        iconName: String,
    ) = withContext(backgroundDispatcher) {
        dbRef.categoryTableQueries.updateCategory(
            id = id,
            name = name,
            iconName = iconName,
        )
    }

    suspend fun countTransactionsForCategory(categoryId: Long): Long = withContext(backgroundDispatcher) {
        return@withContext dbRef.categoryTableQueries.countTransactionsForCategory(categoryId)
            .executeAsOne()
    }

    private fun seedDefaultsIfNeeded() = runBlocking(backgroundDispatcher) {
        val account = dbRef.accountTableQueries.selectDefaultAccount().executeAsOneOrNull()
        if (account == null) {
            dbRef.accountTableQueries.insertAccount(
                name = "Default Account",
                currencyCode = "EUR",
                currencySymbol = "€",
                currencyDecimalPlaces = 2.toLong(),
                isDefault = 1.toLong(),
                createdAtMillis = Clock.System.now().toEpochMilliseconds(),
            )
        }

        if (dbRef.categoryTableQueries.selectAll().executeAsList().isEmpty()) {
            dbRef.transactionWithContext(backgroundDispatcher) {
                defaultCategories.forEach { category ->
                    dbRef.categoryTableQueries.insertCategory(
                        name = category.name,
                        type = category.type,
                        iconName = category.iconName,
                        isSystem = 1.toLong(),
                        createdAtMillis = category.createdAtMillis,
                    )
                }
            }
        }
    }

    private suspend fun Transacter.transactionWithContext(
        coroutineContext: CoroutineContext,
        noEnclosing: Boolean = false,
        body: TransactionWithoutReturn.() -> Unit,
    ) {
        withContext(coroutineContext) {
            this@transactionWithContext.transaction(noEnclosing) {
                body()
            }
        }
    }

    companion object {
        const val DB_FILE_NAME_WITH_EXTENSION = "MoneyFlow.db"
        const val DB_FILE_NAME = "MoneyFlow"
        const val APP_DATABASE_NAME_PROD = "MoneyFlowDB"
        const val APP_DATABASE_NAME_DEBUG = "MoneyFlowDB-debug"
        const val DATABASE_NAME = APP_DATABASE_NAME_PROD
    }
}

package com.prof18.moneyflow.database

import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.Transacter
import app.cash.sqldelight.TransactionWithoutReturn
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrDefault
import app.cash.sqldelight.db.SqlDriver
import com.prof18.moneyflow.database.default.defaultCategories
import com.prof18.moneyflow.database.model.Currency
import com.prof18.moneyflow.database.model.TransactionType
import com.prof18.moneyflow.db.AccountTable
import com.prof18.moneyflow.db.CategoryTable
import com.prof18.moneyflow.db.MoneyFlowDB
import com.prof18.moneyflow.db.MonthlyRecapTable
import com.prof18.moneyflow.db.SelectLatestTransactions
import com.prof18.moneyflow.db.SelectTransactionsPaginated
import com.prof18.moneyflow.db.TransactionTable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.coroutines.CoroutineContext
import kotlin.time.Clock
import kotlin.time.Instant

class DatabaseHelper(
    private val sqlDriver: SqlDriver,
    dispatcher: CoroutineDispatcher? = null,
) {
    private val backgroundDispatcher: CoroutineDispatcher = dispatcher ?: Dispatchers.Default

    private val dbRef: MoneyFlowDB = MoneyFlowDB(
        driver = sqlDriver,
        AccountTableAdapter = AccountTable.Adapter(
            currencyAdapter = EnumColumnAdapter(),
        ),
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

    fun close() {
        sqlDriver.close()
    }

    fun selectLatestTransactions(): Flow<List<SelectLatestTransactions>> =
        dbRef.transactionTableQueries
            .selectLatestTransactions()
            .asFlow()
            .mapToList(backgroundDispatcher)
            .flowOn(backgroundDispatcher)

    fun selectAllCategories(): Flow<List<CategoryTable>> =
        dbRef.categoryTableQueries
            .selectAll()
            .asFlow()
            .mapToList(backgroundDispatcher)
            .flowOn(backgroundDispatcher)

    fun selectCurrentMonthlyRecap(): Flow<MonthlyRecapTable> {
        val current = Clock.System.now().toEpochMilliseconds()
        val id = generateCurrentMonthId(current)
        return dbRef.monthlyRecapTableQueries
            .selectCurrentMonthlyRecap(id)
            .asFlow()
            .mapToOneOrDefault(
                MonthlyRecapTable(
                    id = id,
                    incomeAmount = 0.0,
                    outcomeAmount = 0.0,
                ),
                backgroundDispatcher,
            )
            .flowOn(backgroundDispatcher)
    }

    fun selectCurrentAccount(): Flow<AccountTable> =
        dbRef.accountTableQueries
            .getAccount()
            .asFlow()
            .mapToOneOrDefault(
                AccountTable(
                    id = 1,
                    name = "Default Account",
                    currency = Currency.EURO,
                    amount = 0.0,
                ),
                backgroundDispatcher,
            )
            .flowOn(backgroundDispatcher)

    suspend fun insertTransaction(
        dateMillis: Long,
        amount: Double,
        description: String?,
        categoryId: Long,
        transactionType: TransactionType,
        monthId: Long,
        monthlyIncomeAmount: Double,
        monthlyOutcomeAmount: Double,
    ) {
        dbRef.transactionWithContext(backgroundDispatcher) {
            dbRef.transactionTableQueries.insertTransaction(
                dateMillis = dateMillis,
                amount = amount,
                description = description,
                categoryId = categoryId,
                type = transactionType,
            )

            dbRef.accountTableQueries.updateAmount(
                newTransaction = amount,
                id = 1, // no multi-account support for now
            )

            when (transactionType) {
                TransactionType.INCOME -> {
                    dbRef.monthlyRecapTableQueries.updateIncome(
                        income = monthlyIncomeAmount,
                        id = monthId,
                    )
                }

                TransactionType.OUTCOME -> {
                    dbRef.monthlyRecapTableQueries.updateOutcome(
                        outcome = monthlyOutcomeAmount,
                        id = monthId,
                    )
                }
            }
        }
    }

    suspend fun deleteTransaction(
        transactionId: Long,
        transactionType: TransactionType,
        transactionAmountToUpdate: Double,
        monthId: Long,
        monthlyIncomeAmount: Double,
        monthlyOutcomeAmount: Double,
    ) {
        dbRef.transactionWithContext(backgroundDispatcher) {
            dbRef.transactionTableQueries.deleteTransaction(transactionId)

            dbRef.accountTableQueries.updateAmount(
                newTransaction = transactionAmountToUpdate,
                id = 1, // No multi-account support
            )

            when (transactionType) {
                TransactionType.INCOME -> {
                    dbRef.monthlyRecapTableQueries.updateIncome(
                        income = monthlyIncomeAmount,
                        id = monthId,
                    )
                }

                TransactionType.OUTCOME -> {
                    dbRef.monthlyRecapTableQueries.updateOutcome(
                        outcome = monthlyOutcomeAmount,
                        id = monthId,
                    )
                }
            }
        }
    }

    suspend fun getMonthlyRecap(currentMonthID: Long): MonthlyRecapTable =
        withContext(backgroundDispatcher) {
            var recap = dbRef.monthlyRecapTableQueries.selectCurrentMonthlyRecap(currentMonthID)
                .executeAsList().firstOrNull()
            if (recap == null) {
                dbRef.monthlyRecapTableQueries.insertMonthRecap(
                    id = currentMonthID,
                    incomeAmount = 0.0,
                    outcomeAmount = 0.0,
                )
                recap = MonthlyRecapTable(currentMonthID, 0.0, 0.0)
            }
            return@withContext recap
        }

    suspend fun getTransaction(transactionId: Long): TransactionTable? =
        withContext(backgroundDispatcher) {
            return@withContext dbRef.transactionTableQueries.selectTransaction(transactionId)
                .executeAsOneOrNull()
        }

    suspend fun getTransactionsPaginated(
        pageNum: Long,
        pageSize: Long,
    ): List<SelectTransactionsPaginated> = withContext(backgroundDispatcher) {
        return@withContext dbRef.transactionTableQueries
            .selectTransactionsPaginated(pageSize = pageSize, pageNum = pageNum)
            .executeAsList()
    }

    private fun seedDefaultsIfNeeded() = runBlocking(backgroundDispatcher) {
        val account = dbRef.accountTableQueries.getAccount().executeAsOneOrNull()
        if (account == null) {
            dbRef.accountTableQueries.insertAccount(
                name = "Default Account",
                currency = Currency.EURO,
                amount = 0.0,
            )
        }

        if (dbRef.categoryTableQueries.selectAll().executeAsList().isEmpty()) {
            dbRef.transactionWithContext(backgroundDispatcher) {
                defaultCategories.forEach { category ->
                    dbRef.categoryTableQueries.insertCategory(
                        name = category.name,
                        type = category.type,
                        iconName = category.iconName,
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

    private fun generateCurrentMonthId(millisSinceEpoch: Long): Long {
        val dateTime = Instant.fromEpochMilliseconds(millisSinceEpoch)
            .toLocalDateTime(TimeZone.currentSystemDefault())
        return "${dateTime.year}${dateTime.month.ordinal + 1}".toLong()
    }

    companion object {
        const val DB_FILE_NAME_WITH_EXTENSION = "MoneyFlow.db"
        const val DB_FILE_NAME = "MoneyFlow"
        const val APP_DATABASE_NAME_PROD = "MoneyFlowDB"
        const val APP_DATABASE_NAME_DEBUG = "MoneyFlowDB-debug"
        const val DATABASE_NAME = APP_DATABASE_NAME_PROD
    }
}

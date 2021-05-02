package com.prof18.moneyflow.data.db

import com.prof18.moneyflow.db.*
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrDefault
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import com.prof18.moneyflow.data.db.model.Currency
import com.prof18.moneyflow.data.db.model.TransactionType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import com.prof18.moneyflow.utils.CurrentMonthID
import com.prof18.moneyflow.utils.MillisSinceEpoch
import com.prof18.moneyflow.utils.Utils.generateCurrentMonthId

class DatabaseSourceImpl(
    // not private and mutable to close and reopen the com.prof18.moneyflow.database at runtime
    var dbRef: MoneyFlowDB,
    dispatcher: CoroutineDispatcher?
) : DatabaseSource {

    private val backgroundDispatcher = dispatcher ?: Dispatchers.Main

    override fun selectAllTransaction(): Flow<List<SelectAllTransactions>> =
        dbRef.transactionTableQueries
            .selectAllTransactions()
            .asFlow()
            .mapToList()
            .flowOn(backgroundDispatcher)

    override fun selectAllCategories(): Flow<List<CategoryTable>> =
        dbRef.categoryTableQueries
            .selectAll()
            .asFlow()
            .mapToList()
            .flowOn(backgroundDispatcher)


    override fun selectCurrentMonthlyRecap(): Flow<MonthlyRecapTable> {
        val current = Clock.System.now()
        val id = current.toEpochMilliseconds().generateCurrentMonthId()

        return dbRef.monthlyRecapTableQueries
            .selectCurrentMonthlyRecap(id)
            .asFlow()
            .mapToOneOrDefault(
                MonthlyRecapTable(
                    id = id,
                    incomeAmount = 0.0,
                    outcomeAmount = 0.0
                )
            )
            .flowOn(backgroundDispatcher)
    }


    override fun selectCurrentAccount(): Flow<AccountTable> =
        dbRef.accountTableQueries
            .getAccount()
            .asFlow()
            .mapToOneOrDefault(
                AccountTable(
                    id = 1,
                    name = "Default Account",
                    currency = Currency.EURO,
                    amount = 0.0
                )
            )
            .flowOn(backgroundDispatcher)

    override suspend fun insertTransaction(
        dateMillis: MillisSinceEpoch,
        amount: Double,
        description: String?,
        categoryId: Long,
        transactionType: TransactionType,
        monthId: Long,
        monthlyIncomeAmount: Double,
        monthlyOutcomeAmount: Double
    ) {
        withContext(backgroundDispatcher) {
            dbRef.transaction {
                dbRef.transactionTableQueries.insertTransaction(
                    dateMillis = dateMillis,
                    amount = amount,
                    description = description,
                    categoryId = categoryId,
                    type = transactionType
                )

                dbRef.accountTableQueries.updateAmount(
                    newTransaction = amount,
                    id = 1 // no multi-account support for now
                )

                when (transactionType) {
                    TransactionType.INCOME -> {
                        dbRef.monthlyRecapTableQueries.updateIncome(
                            income = monthlyIncomeAmount,
                            id = monthId
                        )
                    }
                    TransactionType.OUTCOME -> {
                        dbRef.monthlyRecapTableQueries.updateOutcome(
                            outcome = monthlyOutcomeAmount,
                            id = monthId
                        )
                    }
                }
            }
        }
    }

    override suspend fun deleteTransaction(
        transactionId: Long,
        transactionType: TransactionType,
        transactionAmountToUpdate: Double,
        monthId: Long,
        monthlyIncomeAmount: Double,
        monthlyOutcomeAmount: Double
    ) {
        withContext(backgroundDispatcher) {
            dbRef.transaction {
                dbRef.transactionTableQueries.deleteTransaction(transactionId)

                dbRef.accountTableQueries.updateAmount(
                    newTransaction = transactionAmountToUpdate,
                    id = 1 // No multi-account support
                )

                when (transactionType) {
                    TransactionType.INCOME -> {
                        dbRef.monthlyRecapTableQueries.updateIncome(
                            income = monthlyIncomeAmount,
                            id = monthId
                        )
                    }
                    TransactionType.OUTCOME -> {
                        dbRef.monthlyRecapTableQueries.updateOutcome(
                            outcome = monthlyOutcomeAmount,
                            id = monthId
                        )
                    }
                }
            }
        }
    }

    override suspend fun getMonthlyRecap(currentMonthID: CurrentMonthID): MonthlyRecapTable =
        withContext(backgroundDispatcher) {

            var recap = dbRef.monthlyRecapTableQueries.selectCurrentMonthlyRecap(currentMonthID)
                .executeAsList().firstOrNull()
            if (recap == null) {
                dbRef.monthlyRecapTableQueries.insertMonthRecap(
                    id = currentMonthID,
                    incomeAmount = 0.0,
                    outcomeAmount = 0.0
                )
                recap = MonthlyRecapTable(currentMonthID, 0.0, 0.0)
            }
            return@withContext recap
        }


    override suspend fun getTransaction(transactionId: Long): TransactionTable? =
        withContext(backgroundDispatcher) {
            return@withContext dbRef.transactionTableQueries.selectTransaction(transactionId)
                .executeAsOneOrNull()
        }
}
package data.db

import data.db.model.InsertTransactionDTO
import asFlow
import com.prof18.moneyflow.db.*
import data.db.model.Currency
import data.db.model.TransactionType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import mapToList
import mapToOneOrDefault
import transactionWithContext
import kotlin.math.abs


class DatabaseSourceImpl (
    // not private and mutable to close and reopen the database at runtime
    var dbRef: MoneyFlowDB,
    dispatcher: CoroutineDispatcher?
) : DatabaseSource {


    private val backgroundDispatcher = dispatcher ?: Dispatchers.Main

    override fun selectAllTransaction(): Flow<List<TransactionTable>> =
        dbRef.transactionTableQueries
            .selectAll()
            .asFlow()
            .mapToList()
            .flowOn(backgroundDispatcher)

    override suspend fun insertTransaction(insertTransaction: InsertTransactionDTO) {
        dbRef.transactionWithContext(backgroundDispatcher) {
            dbRef.transactionTableQueries.insertTransaction(
                dateMillis = insertTransaction.dateMillis,
                amount = insertTransaction.amount,
                description = insertTransaction.description,
                categoryId = insertTransaction.categoryId,
                type = insertTransaction.transactionType
            )

            // Account recap
            // The id is zero because for the time being there is no multi-account support
            dbRef.accountTableQueries.updateAmount(newTransaction = insertTransaction.amount, id = 1)

            // Monthly recap
            var recap = dbRef.monthlyRecapTableQueries.selectCurrentMonthlyRecap(insertTransaction.currentMonthId).executeAsList().firstOrNull()
            if (recap == null) {
                dbRef.monthlyRecapTableQueries.insertMonthRecap(
                    id = insertTransaction.currentMonthId,
                    incomeAmount = 0.0,
                    outcomeAmount = 0.0
                )
                recap = MonthlyRecapTable(insertTransaction.currentMonthId, 0.0, 0.0)
            }

            when (insertTransaction.transactionType) {
                TransactionType.INCOME -> {
                    val income = recap.incomeAmount + insertTransaction.amount
                    dbRef.monthlyRecapTableQueries.updateIncome(
                        income = income,
                        id = insertTransaction.currentMonthId
                    )
                }
                TransactionType.OUTCOME -> {
                    // We keep the count positive. We know that it is an outcome
                    val outcome = recap.outcomeAmount + abs(insertTransaction.amount)
                    dbRef.monthlyRecapTableQueries.updateOutcome(
                        outcome = outcome,
                        id = insertTransaction.currentMonthId
                    )
                }
            }
        }
    }

    override fun selectAllCategories(): Flow<List<CategoryTable>> =
        dbRef.categoryTableQueries
            .selectAll()
            .asFlow()
            .mapToList()
            .flowOn(backgroundDispatcher)


    override fun selectCurrentMonthlyRecap(): Flow<MonthlyRecapTable> {
        val current = Clock.System.now()
        val dateTime: LocalDateTime = current.toLocalDateTime(TimeZone.currentSystemDefault())
        val id = "${dateTime.year}${dateTime.monthNumber}${dateTime.dayOfMonth}".toLong()

        return  dbRef.monthlyRecapTableQueries
            .selectCurrentMonthlyRecap(id)
            .asFlow()
            .mapToOneOrDefault(MonthlyRecapTable(id = id, incomeAmount = 0.0, outcomeAmount = 0.0))
            .flowOn(backgroundDispatcher)
    }


    override fun selectCurrentAccount(): Flow<AccountTable> =
        dbRef.accountTableQueries
            .getAccount()
            .asFlow()
            .mapToOneOrDefault(AccountTable(id = 1, name = "Default Account", currency = Currency.EURO, amount = 0.0))
            .flowOn(backgroundDispatcher)
}
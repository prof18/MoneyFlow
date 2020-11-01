package data.db

import data.db.model.InsertTransactionDTO
import asFlow
import com.prof18.moneyflow.db.CategoryTable
import com.prof18.moneyflow.db.MoneyFlowDB
import com.prof18.moneyflow.db.MonthlyRecapTable
import com.prof18.moneyflow.db.TransactionTable
import data.db.model.TransactionType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import mapToList
import transactionWithContext
import kotlin.math.abs


class DatabaseSourceImpl (
    // not private and mutable to close and reopen the database at runtime
    var dbRef: MoneyFlowDB,
    private val backgroundDispatcher: CoroutineDispatcher
) : DatabaseSource {

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
            var recap = dbRef.monthlyRecapTableQueries.selectMonthlyRecap().executeAsList().firstOrNull()
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

    /*

    For pagination:
        suspend fun selectById(id: Long): Flow<List<Breed>> =
        dbRef.tableQueries
            .selectById(id)
            .asFlow()
            .mapToList()
            .flowOn(backgroundDispatcher)

     */




}
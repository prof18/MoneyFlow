package data.db

import InsertTransactionDTO
import asFlow
import com.prof18.moneyflow.db.MoneyFlowDB
import com.prof18.moneyflow.db.TransactionTable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import mapToList
import transactionWithContext


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
        }
    }


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
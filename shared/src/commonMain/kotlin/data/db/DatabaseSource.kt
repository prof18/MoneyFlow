package data.db

import InsertTransactionDTO
import asFlow
import com.prof18.moneyflow.db.MoneyFlowDB
import com.prof18.moneyflow.db.TransactionTable
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import mapToList
import transactionWithContext

interface DatabaseSource {
    fun selectAllTransaction(): Flow<List<TransactionTable>>
    suspend fun insertTransaction(insertTransaction: InsertTransactionDTO)
}
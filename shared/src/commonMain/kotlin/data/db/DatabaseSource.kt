package data.db

import data.db.model.InsertTransactionDTO
import com.prof18.moneyflow.db.CategoryTable
import com.prof18.moneyflow.db.TransactionTable
import kotlinx.coroutines.flow.Flow

interface DatabaseSource {
    fun selectAllTransaction(): Flow<List<TransactionTable>>
    fun selectAllCategories(): Flow<List<CategoryTable>>
    suspend fun insertTransaction(insertTransaction: InsertTransactionDTO)
}
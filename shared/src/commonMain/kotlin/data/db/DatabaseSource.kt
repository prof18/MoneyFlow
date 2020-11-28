package data.db

import com.prof18.moneyflow.db.*
import data.db.model.InsertTransactionDTO
import kotlinx.coroutines.flow.Flow

interface DatabaseSource {
    fun selectAllTransaction(): Flow<List<SelectAllTransactions>>
    fun selectAllCategories(): Flow<List<CategoryTable>>
    fun selectCurrentMonthlyRecap(): Flow<MonthlyRecapTable>
    fun selectCurrentAccount(): Flow<AccountTable>
    suspend fun insertTransaction(insertTransaction: InsertTransactionDTO)
}
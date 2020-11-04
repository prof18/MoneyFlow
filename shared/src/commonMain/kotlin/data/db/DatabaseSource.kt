package data.db

import com.prof18.moneyflow.db.AccountTable
import data.db.model.InsertTransactionDTO
import com.prof18.moneyflow.db.CategoryTable
import com.prof18.moneyflow.db.MonthlyRecapTable
import com.prof18.moneyflow.db.TransactionTable
import kotlinx.coroutines.flow.Flow

interface DatabaseSource {
    fun selectAllTransaction(): Flow<List<TransactionTable>>
    fun selectAllCategories(): Flow<List<CategoryTable>>
    fun selectCurrentMonthlyRecap(): Flow<MonthlyRecapTable>
    fun selectCurrentAccount(): Flow<AccountTable>
    suspend fun insertTransaction(insertTransaction: InsertTransactionDTO)
}
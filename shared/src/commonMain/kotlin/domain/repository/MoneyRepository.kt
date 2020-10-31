package domain.repository

import InsertTransactionDTO
import domain.model.BalanceRecap
import domain.model.Category
import domain.model.MoneyTransaction
import kotlinx.coroutines.flow.Flow

interface MoneyRepository {

    @Throws(Exception::class)
    suspend fun getBalanceRecap(): Flow<BalanceRecap>

    @Throws(Exception::class)
    suspend fun getLatestTransactions(): Flow<List<MoneyTransaction>>

    @Throws(Exception::class)
    suspend fun insertTransaction(transactionDTO: InsertTransactionDTO)

    @Throws(Exception::class)
    suspend fun getCategories(): Flow<List<Category>>

}
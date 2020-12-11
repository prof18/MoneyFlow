package domain.repository

import domain.entities.BalanceRecap
import domain.entities.Category
import domain.entities.MoneyTransaction
import kotlinx.coroutines.flow.Flow
import presentation.addtransaction.TransactionToSave

interface MoneyRepository {

    @Throws(Exception::class)
    suspend fun getBalanceRecap(): Flow<BalanceRecap>

    @Throws(Exception::class)
    suspend fun getLatestTransactions(): Flow<List<MoneyTransaction>>

    @Throws(Exception::class)
    suspend fun insertTransaction(transactionToSave: TransactionToSave)

    @Throws(Exception::class)
    suspend fun getCategories(): Flow<List<Category>>

    @Throws(Exception::class)
    suspend fun deleteTransaction(transactionId: Long)

}
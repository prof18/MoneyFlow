package domain.repository

import InsertTransactionDTO
import domain.model.BalanceRecap
import domain.model.MoneyTransaction
import kotlinx.coroutines.flow.Flow

interface MoneyRepository {

    suspend fun refreshData()

    @Throws(Exception::class)
    suspend fun getBalanceRecap(): Flow<BalanceRecap>

    @Throws(Exception::class)
    suspend fun getLatestTransactions(): Flow<List<MoneyTransaction>>

    @Throws(Exception::class)
    suspend fun insertTransaction(transactionDTO: InsertTransactionDTO)

}
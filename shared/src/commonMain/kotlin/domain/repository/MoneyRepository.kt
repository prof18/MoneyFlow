package domain.repository

import domain.model.BalanceRecap
import domain.model.MoneyTransaction
import kotlinx.coroutines.flow.Flow

interface MoneyRepository {

    suspend fun refreshData()

    suspend fun getBalanceRecap(): Flow<BalanceRecap>

    suspend fun getLatestTransactions(): Flow<List<MoneyTransaction>>

}
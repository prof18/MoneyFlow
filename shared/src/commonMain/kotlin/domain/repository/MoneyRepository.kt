package domain.repository

import domain.model.BalanceRecap
import domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface MoneyRepository {

    fun getBalanceRecap(): Flow<BalanceRecap>

    fun getLatestTransactions(): Flow<List<Transaction>>

}
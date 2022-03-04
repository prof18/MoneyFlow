package com.prof18.moneyflow.domain.repository

import com.prof18.moneyflow.domain.entities.BalanceRecap
import com.prof18.moneyflow.domain.entities.Category
import com.prof18.moneyflow.domain.entities.MoneySummary
import com.prof18.moneyflow.domain.entities.MoneyTransaction
import com.prof18.moneyflow.presentation.addtransaction.TransactionToSave
import kotlinx.coroutines.flow.Flow

interface MoneyRepository {

    fun getBalanceRecap(): Flow<BalanceRecap>

    fun getLatestTransactions(): Flow<List<MoneyTransaction>>

    suspend fun insertTransaction(transactionToSave: TransactionToSave)

    fun getCategories(): Flow<List<Category>>

    suspend fun deleteTransaction(transactionId: Long)

    fun getMoneySummary(): Flow<MoneySummary>

    suspend fun getTransactionsPaginated(pageNum: Long, pageSize: Long): List<MoneyTransaction>

    companion object {
        const val DEFAULT_PAGE_SIZE = 30L
    }
}

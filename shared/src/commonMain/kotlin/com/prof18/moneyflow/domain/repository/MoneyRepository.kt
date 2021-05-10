package com.prof18.moneyflow.domain.repository

import com.prof18.moneyflow.domain.entities.BalanceRecap
import com.prof18.moneyflow.domain.entities.Category
import com.prof18.moneyflow.domain.entities.MoneySummary
import com.prof18.moneyflow.domain.entities.MoneyTransaction
import kotlinx.coroutines.flow.Flow
import com.prof18.moneyflow.presentation.addtransaction.TransactionToSave

interface MoneyRepository {

    @Throws(Exception::class)
    fun getBalanceRecap(): Flow<BalanceRecap>

    @Throws(Exception::class)
    fun getLatestTransactions(): Flow<List<MoneyTransaction>>

    @Throws(Exception::class)
    suspend fun insertTransaction(transactionToSave: TransactionToSave)

    @Throws(Exception::class)
    suspend fun getCategories(): Flow<List<Category>>

    @Throws(Exception::class)
    suspend fun deleteTransaction(transactionId: Long)

    @Throws(Exception::class)
    fun getMoneySummary(): Flow<MoneySummary>
}
package com.prof18.moneyflow.presentation.home

import kotlinx.coroutines.flow.StateFlow

interface HomeUseCase {
    fun observeHomeModel(): StateFlow<HomeModel>
    fun computeData()
    suspend fun computeHomeDataSuspendable()
    suspend fun deleteTransactionSuspendable(transactionId: Long)
    fun deleteTransaction(transactionId: Long)
}
package com.prof18.moneyflow.presentation.home

import kotlinx.coroutines.flow.Flow

interface HomeUseCase {
    fun observeHomeModel(): Flow<HomeModel>
    suspend fun deleteTransaction(transactionId: Long)
}
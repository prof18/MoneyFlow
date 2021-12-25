package com.prof18.moneyflow.presentation.addtransaction

import com.prof18.moneyflow.domain.repository.MoneyRepository

class AddTransactionUseCase(
    private val moneyRepository: MoneyRepository
) {
    suspend fun insertTransaction(transactionToSave: TransactionToSave) {
        moneyRepository.insertTransaction(transactionToSave)
    }
}
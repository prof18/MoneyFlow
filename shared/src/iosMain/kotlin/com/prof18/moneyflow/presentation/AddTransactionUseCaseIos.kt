package com.prof18.moneyflow.presentation

import com.prof18.moneyflow.presentation.addtransaction.AddTransactionUseCase
import com.prof18.moneyflow.presentation.addtransaction.TransactionToSave
import kotlinx.coroutines.launch

class AddTransactionUseCaseIos(
    private val addTransactionUseCase: AddTransactionUseCase
): BaseUseCaseIos() {

    fun insertTransaction(transactionToSave: TransactionToSave) {
        scope.launch { addTransactionUseCase.insertTransaction(transactionToSave) }
    }
}
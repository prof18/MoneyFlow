package com.prof18.moneyflow.presentation

import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.domain.entities.doOnError
import com.prof18.moneyflow.presentation.addtransaction.AddTransactionUseCase
import com.prof18.moneyflow.presentation.addtransaction.TransactionToSave
import kotlinx.coroutines.launch

class AddTransactionUseCaseIos(
    private val addTransactionUseCase: AddTransactionUseCase
) : BaseUseCaseIos() {

    fun insertTransaction(transactionToSave: TransactionToSave, onError: (MoneyFlowError) -> Unit) {
        scope.launch {
            val result: MoneyFlowResult<Unit> =
                addTransactionUseCase.insertTransaction(transactionToSave)
            result.doOnError { onError(it) }
        }
    }
}
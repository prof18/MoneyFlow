package com.prof18.moneyflow.presentation

import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.presentation.addtransaction.AddTransactionUseCase
import com.prof18.moneyflow.presentation.addtransaction.TransactionToSave
import com.prof18.moneyflow.presentation.model.UIErrorMessage
import kotlinx.coroutines.launch

@ObjCName("AddTransactionUseCase")
class AddTransactionUseCaseIos(
    private val addTransactionUseCase: AddTransactionUseCase,
) : BaseUseCaseIos() {

    fun insertTransaction(
        transactionToSave: TransactionToSave,
        onSuccess: () -> Unit,
        onError: (UIErrorMessage) -> Unit,
    ) {
        scope.launch {
            val result: MoneyFlowResult<Unit> = addTransactionUseCase.insertTransaction(
                transactionToSave,
            )
            when (result) {
                is MoneyFlowResult.Success -> onSuccess()
                is MoneyFlowResult.Error -> onError(result.uiErrorMessage)
            }
        }
    }
}

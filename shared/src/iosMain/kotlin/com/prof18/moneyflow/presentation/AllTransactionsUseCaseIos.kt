package com.prof18.moneyflow.presentation

import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.domain.entities.MoneyTransaction
import com.prof18.moneyflow.domain.repository.MoneyRepository
import com.prof18.moneyflow.presentation.alltransactions.AllTransactionsUseCase
import kotlinx.coroutines.launch

class AllTransactionsUseCaseIos(
    private val allTransactionsUseCase: AllTransactionsUseCase
) : BaseUseCaseIos() {

    fun getTransactionsPaginated(
        pageNum: Long,
        pageSize: Long,
        onSuccess: (List<MoneyTransaction>) -> Unit,
        onError: (MoneyFlowError) -> Unit,
    ) {
        scope.launch {
            when (val result = allTransactionsUseCase.getTransactionsPaginated(pageNum, pageSize)) {
                is MoneyFlowResult.Error -> onError(result.moneyFlowError)
                is MoneyFlowResult.Success -> onSuccess(result.data)
            }
        }
    }
}
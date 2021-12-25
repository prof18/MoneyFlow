package com.prof18.moneyflow.presentation

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
        onError: (Throwable) -> Unit,
    ) {
        scope.launch {
            try {
                val transactions = allTransactionsUseCase.getTransactionsPaginated(pageNum, pageSize)
                onSuccess(transactions)
            } catch (e: Throwable) {
                e.printStackTrace()
                onError(e)
            }
        }
    }
}
package com.prof18.moneyflow.presentation

import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneyTransaction
import com.prof18.moneyflow.presentation.alltransactions.AllTransactionsUseCase
import com.prof18.moneyflow.presentation.model.UIErrorMessage
import com.prof18.moneyflow.utils.logError
import kotlinx.coroutines.launch

@ObjCName("AllTransactionsUseCase")
class AllTransactionsUseCaseIos(
    private val allTransactionsUseCase: AllTransactionsUseCase,
    private val errorMapper: MoneyFlowErrorMapper,
) : BaseUseCaseIos() {

    fun getTransactionsPaginated(
        pageNum: Long,
        pageSize: Long,
        onSuccess: (List<MoneyTransaction>) -> Unit,
        onError: (UIErrorMessage) -> Unit,
    ) {
        scope.launch {
            try {
                val data = allTransactionsUseCase.getTransactionsPaginated(pageNum, pageSize)
                onSuccess(data)
            } catch (throwable: Throwable) {
                val error = MoneyFlowError.GetAllTransaction(throwable)
                throwable.logError(error)
                val errorMessage = errorMapper.getUIErrorMessage(error)
                onError(errorMessage)
            }
        }
    }
}

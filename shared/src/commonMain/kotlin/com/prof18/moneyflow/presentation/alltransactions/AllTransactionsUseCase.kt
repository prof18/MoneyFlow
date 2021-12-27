package com.prof18.moneyflow.presentation.alltransactions

import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.domain.entities.MoneyTransaction
import com.prof18.moneyflow.domain.repository.MoneyRepository
import com.prof18.moneyflow.utils.logError

class AllTransactionsUseCase(
    private val moneyRepository: MoneyRepository
) {
    suspend fun getTransactionsPaginated(
        pageNum: Long, pageSize: Long
    ): MoneyFlowResult<List<MoneyTransaction>> {
        return try {
            MoneyFlowResult.Success(moneyRepository.getTransactionsPaginated(pageNum, pageSize))
        } catch (throwable: Throwable) {
            val error = MoneyFlowError.GetAllTransaction(throwable)
            throwable.logError(error)
            MoneyFlowResult.Error(error)
        }
    }
}
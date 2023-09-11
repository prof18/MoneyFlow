package com.prof18.moneyflow.presentation.addtransaction

import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.domain.repository.MoneyRepository
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.utils.logError
import kotlin.native.HiddenFromObjC

@HiddenFromObjC
class AddTransactionUseCase(
    private val moneyRepository: MoneyRepository,
    private val errorMapper: MoneyFlowErrorMapper,
) {
    suspend fun insertTransaction(transactionToSave: TransactionToSave): MoneyFlowResult<Unit> {
        return try {
            moneyRepository.insertTransaction(transactionToSave)
            MoneyFlowResult.Success(Unit)
        } catch (throwable: Throwable) {
            val error = MoneyFlowError.AddTransaction(throwable)
            throwable.logError(error)
            val errorMessage = errorMapper.getUIErrorMessage(error)
            MoneyFlowResult.Error(errorMessage)
        }
    }
}

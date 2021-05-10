package com.prof18.moneyflow.presentation

import com.prof18.moneyflow.FlowWrapper
import com.prof18.moneyflow.domain.repository.MoneyRepository
import kotlinx.coroutines.launch

class HomeUseCaseIos(
    private val moneyRepository: MoneyRepository,
): BaseUseCaseIos() {

    fun getMoneySummary() = FlowWrapper(scope, moneyRepository.getMoneySummary())

    fun deleteTransaction(transactionId: Long) {
        scope.launch { moneyRepository.deleteTransaction(transactionId) }
    }
}
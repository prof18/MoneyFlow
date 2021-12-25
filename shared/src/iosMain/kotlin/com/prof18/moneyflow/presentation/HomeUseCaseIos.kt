package com.prof18.moneyflow.presentation

import co.touchlab.stately.freeze
import com.prof18.moneyflow.FlowWrapper
import com.prof18.moneyflow.presentation.home.HomeModel
import com.prof18.moneyflow.presentation.home.HomeUseCase
import kotlinx.coroutines.launch

class HomeUseCaseIos(
    private val homeUseCase: HomeUseCase
) : BaseUseCaseIos() {

    val hideSensibleDataState: FlowWrapper<Boolean> =
        FlowWrapper(scope, homeUseCase.hideSensibleDataState)

    fun getMoneySummary(): FlowWrapper<HomeModel> =
        FlowWrapper(scope, homeUseCase.observeHomeModel().freeze())

    fun deleteTransaction(transactionId: Long) {
        scope.launch {
            homeUseCase.deleteTransaction(transactionId)
        }
    }

    fun toggleHideSensitiveData(status: Boolean) {
        homeUseCase.toggleHideSensitiveData(status)
    }
}
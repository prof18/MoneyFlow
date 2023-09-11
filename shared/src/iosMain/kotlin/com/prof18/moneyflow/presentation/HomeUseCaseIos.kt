package com.prof18.moneyflow.presentation

import com.prof18.moneyflow.FlowWrapper
import com.prof18.moneyflow.domain.entities.doOnError
import com.prof18.moneyflow.presentation.home.HomeModel
import com.prof18.moneyflow.presentation.home.HomeUseCase
import com.prof18.moneyflow.presentation.model.UIErrorMessage
import kotlinx.coroutines.launch

@ObjCName("HomeUseCase")
class HomeUseCaseIos(
    private val homeUseCase: HomeUseCase,
) : BaseUseCaseIos() {

    val hideSensibleDataState: FlowWrapper<Boolean> =
        FlowWrapper(scope, homeUseCase.hideSensibleDataState)

    fun getMoneySummary(): FlowWrapper<HomeModel> =
        FlowWrapper(scope, homeUseCase.observeHomeModel())

    fun deleteTransaction(transactionId: Long, onError: (UIErrorMessage) -> Unit) {
        scope.launch {
            val result = homeUseCase.deleteTransaction(transactionId)
            result.doOnError { onError(it) }
        }
    }

    fun toggleHideSensitiveData(status: Boolean) {
        homeUseCase.toggleHideSensitiveData(status)
    }
}

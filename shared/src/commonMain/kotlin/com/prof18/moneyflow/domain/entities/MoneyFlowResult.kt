package com.prof18.moneyflow.domain.entities

import com.prof18.moneyflow.presentation.model.UIErrorMessage

internal sealed class MoneyFlowResult<out T> {
    data class Success<T>(val data: T) : MoneyFlowResult<T>()
    data class Error(val uiErrorMessage: UIErrorMessage) : MoneyFlowResult<Nothing>()
}

internal fun <T> MoneyFlowResult<T>.doOnError(
    onError: (UIErrorMessage) -> Unit,
) {
    if (this is MoneyFlowResult.Error) {
        onError(this.uiErrorMessage)
    }
}

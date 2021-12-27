package com.prof18.moneyflow.domain.entities

sealed class MoneyFlowResult<out T> {
    data class Success<T>(val data: T) : MoneyFlowResult<T>()
    data class Error(val moneyFlowError: MoneyFlowError) : MoneyFlowResult<Nothing>()
}

fun <T> MoneyFlowResult<T>.doOnError(
    onError: (MoneyFlowError) -> Unit,
) {
    if (this is MoneyFlowResult.Error) {
        onError(this.moneyFlowError)
    }
}
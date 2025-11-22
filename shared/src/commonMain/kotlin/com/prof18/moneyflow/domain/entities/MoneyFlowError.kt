package com.prof18.moneyflow.domain.entities

@Suppress("MagicNumber")
sealed class MoneyFlowError(val code: Int, val throwable: Throwable) {
    class GetMoneySummary(throwable: Throwable) : MoneyFlowError(100, throwable)
    class DeleteTransaction(throwable: Throwable) : MoneyFlowError(101, throwable)
    class AddTransaction(throwable: Throwable) : MoneyFlowError(102, throwable)
    class GetAllTransaction(throwable: Throwable) : MoneyFlowError(103, throwable)
    class GetCategories(throwable: Throwable) : MoneyFlowError(104, throwable)
    class DatabaseExport(throwable: Throwable) : MoneyFlowError(105, throwable)
    class DatabaseImport(throwable: Throwable) : MoneyFlowError(106, throwable)
    class DatabaseNotFound(throwable: Throwable) : MoneyFlowError(107, throwable)
}

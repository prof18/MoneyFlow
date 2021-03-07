package com.prof18.moneyflow.presentation.home

import com.prof18.moneyflow.domain.entities.BalanceRecap
import com.prof18.moneyflow.domain.entities.MoneyTransaction

sealed class HomeModel {
    object Loading: HomeModel()
    data class Error(val message: String): HomeModel()
    data class HomeState(val balanceRecap: BalanceRecap, val latestTransactions: List<MoneyTransaction>): HomeModel()

    override fun toString(): String {
        return when(this) {
            is Loading -> "Loading state"
            is Error -> "Error state"
            is HomeState -> "Home State, $this"
        }
    }
}
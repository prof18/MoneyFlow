package com.prof18.moneyflow.presentation.home

import com.prof18.moneyflow.domain.entities.BalanceRecap
import com.prof18.moneyflow.domain.entities.CurrencyConfig
import com.prof18.moneyflow.domain.entities.MoneyTransaction
import com.prof18.moneyflow.presentation.model.UIErrorMessage

sealed class HomeModel {
    data object Loading : HomeModel()
    data class Error(val uiErrorMessage: UIErrorMessage) : HomeModel()
    data class HomeState(
        val balanceRecap: BalanceRecap,
        val latestTransactions: List<MoneyTransaction>,
        val currencyConfig: CurrencyConfig,
    ) : HomeModel()
}

package com.prof18.moneyflow.domain.entities

internal data class MoneySummary(
    val balanceRecap: BalanceRecap,
    val latestTransactions: List<MoneyTransaction>,
    val currencyConfig: CurrencyConfig,
)

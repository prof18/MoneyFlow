package com.prof18.moneyflow.domain.entities

data class MoneySummary(
    val balanceRecap: BalanceRecap,
    val latestTransactions: List<MoneyTransaction>,
)

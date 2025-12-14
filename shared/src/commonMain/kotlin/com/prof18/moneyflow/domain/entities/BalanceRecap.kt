package com.prof18.moneyflow.domain.entities

data class BalanceRecap(
    val totalBalanceCents: Long,
    val monthlyIncomeCents: Long,
    val monthlyExpensesCents: Long,
)

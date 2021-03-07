package com.prof18.moneyflow.domain.entities

import com.prof18.moneyflow.presentation.CategoryIcon

data class MoneyTransaction(
    val id: Long,
    val title: String,
    val icon: CategoryIcon,
    val amount: Double,
    val type: TransactionTypeUI,
    val formattedDate: String
)
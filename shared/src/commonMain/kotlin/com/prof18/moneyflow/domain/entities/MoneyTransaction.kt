package com.prof18.moneyflow.domain.entities

import com.prof18.moneyflow.presentation.model.CategoryIcon
data class MoneyTransaction(
    val id: Long,
    val title: String,
    val icon: CategoryIcon,
    val amountCents: Long,
    val type: TransactionTypeUI,
    val milliseconds: Long,
    val formattedDate: String,
)

package com.prof18.moneyflow.domain.entities

import com.prof18.moneyflow.presentation.model.CategoryIcon
import com.prof18.moneyflow.utils.MillisSinceEpoch

data class MoneyTransaction(
    val id: Long,
    val title: String,
    val icon: CategoryIcon,
    val amount: Double,
    val type: TransactionTypeUI,
    val milliseconds: MillisSinceEpoch,
    val formattedDate: String,
)

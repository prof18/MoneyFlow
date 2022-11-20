package com.prof18.moneyflow.presentation.addtransaction

import com.prof18.moneyflow.data.db.model.TransactionType
import com.prof18.moneyflow.utils.MillisSinceEpoch

data class TransactionToSave(
    val dateMillis: MillisSinceEpoch,
    val amount: Double,
    val description: String?,
    val categoryId: Long,
    val transactionType: TransactionType,
)

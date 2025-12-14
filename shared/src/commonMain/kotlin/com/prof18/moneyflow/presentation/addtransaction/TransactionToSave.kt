package com.prof18.moneyflow.presentation.addtransaction

import com.prof18.moneyflow.database.model.TransactionType

internal data class TransactionToSave(
    val dateMillis: Long,
    val amountCents: Long,
    val description: String?,
    val categoryId: Long,
    val transactionType: TransactionType,
)

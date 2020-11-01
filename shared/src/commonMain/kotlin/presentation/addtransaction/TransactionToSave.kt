package presentation.addtransaction

import data.db.model.TransactionType
import utils.MillisSinceEpoch

data class TransactionToSave(
    val dateMillis: MillisSinceEpoch,
    val amount: Double,
    val description: String?,
    val categoryId: Long,
    val transactionType: TransactionType
)
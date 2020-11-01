package data.db.model

import utils.CurrentMonthID
import utils.MillisSinceEpoch

data class InsertTransactionDTO(
    val dateMillis: MillisSinceEpoch,
    val amount: Double,
    val description: String?,
    val categoryId: Long,
    val transactionType: TransactionType,
    val currentMonthId: CurrentMonthID
)
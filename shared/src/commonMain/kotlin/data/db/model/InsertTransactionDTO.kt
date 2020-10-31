import data.db.model.TransactionType;

data class InsertTransactionDTO(
    val dateMillis: Long,
    val amount: Double,
    val description: String?,
    val categoryId: Long,
    val transactionType: TransactionType
)
package domain.model

data class Transaction(
    val id: String,
    val title: String,
    val amount: Int,
    val type: TransactionType,
    val formattedDate: String
)
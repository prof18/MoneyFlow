package domain.entities

data class MoneyTransaction(
    val id: Long,
    val title: String,
    val amount: Double,
    val type: TransactionTypeUI,
    val formattedDate: String
)
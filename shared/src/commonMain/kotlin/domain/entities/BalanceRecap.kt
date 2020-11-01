package domain.entities

data class BalanceRecap(
    val totalBalance: Int,
    val monthlyIncome: Int,
    val monthlyExpenses: Int
)
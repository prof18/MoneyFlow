import com.prof18.moneyflow.domain.entities.BalanceRecap

object DataFactory {

    fun getEmptyBalanceRecap(): BalanceRecap {
        return BalanceRecap(
            totalBalance = 0.0,
            monthlyIncome = 0.0,
            monthlyExpenses = 0.0,
        )
    }

    const val dayMillis: Long = 86400
}

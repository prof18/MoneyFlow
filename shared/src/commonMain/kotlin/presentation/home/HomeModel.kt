package presentation.home

import domain.model.BalanceRecap
import domain.model.Transaction

sealed class HomeModel {
    object Loading: HomeModel()
    data class Error(val message: String): HomeModel()
    data class HomeState(val balanceRecap: BalanceRecap, val latestTransactions: List<Transaction>): HomeModel()

    override fun toString(): String {
        return when(this) {
            is Loading -> "Loading state"
            is Error -> "Error state"
            is HomeState -> "Home State, $this"
        }
    }


}
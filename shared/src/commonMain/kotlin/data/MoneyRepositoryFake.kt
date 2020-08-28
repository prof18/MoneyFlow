package data

import domain.model.BalanceRecap
import domain.model.Transaction
import domain.model.TransactionType
import domain.repository.MoneyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoneyRepositoryFake : MoneyRepository {

    override fun getBalanceRecap(): Flow<BalanceRecap> = flow {
        emit(
            BalanceRecap(
                totalBalance = 1518,
                monthlyIncome = 300,
                monthlyExpenses = 200
            )
        )
    }

    override fun getLatestTransactions(): Flow<List<Transaction>> = flow {
        emit(
            listOf(
                Transaction(
                    id = "gajkhgkagv",
                    title = "Dinner with Friends",
                    amount = 20,
                    type = TransactionType.EXPENSE,
                    formattedDate = "21 Jan 2020"
                ),
                Transaction(
                    id = "gajkhgkagv",
                    title = "Salary",
                    amount = 1000,
                    type = TransactionType.INCOME,
                    formattedDate = "21 Jan 2020"
                ),
                Transaction(
                    id = "gajkhgkagv",
                    title = "Keyboard",
                    amount = 100,
                    type = TransactionType.EXPENSE,
                    formattedDate = "21 Jan 2020"
                ),
                Transaction(
                    id = "gajkhgkagv",
                    title = "Dinner with Friends",
                    amount = 30,
                    type = TransactionType.EXPENSE,
                    formattedDate = "21 Jan 2020"
                ),
            )
        )
    }
}
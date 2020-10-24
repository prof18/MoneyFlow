package data

import domain.model.BalanceRecap
import domain.model.MoneyTransaction
import domain.model.TransactionTypeUI
import domain.repository.MoneyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/*
class MoneyRepositoryFake : MoneyRepository {

    override fun getBalanceRecap(): Flow<BalanceRecap> = flow {
        emit(
            BalanceRecap(
                totalBalance = 1518,
                monthlyIncome = 300,
                monthlyExpenses = 200
            )
        )
        kotlinx.coroutines.delay(5000)
        emit(
            BalanceRecap(
                totalBalance = 1518888888,
                monthlyIncome = 300,
                monthlyExpenses = 200
            )
        )
    }

    override fun getLatestTransactions(): Flow<List<MoneyTransaction>> = flow {
        emit(
            listOf(
                MoneyTransaction(
                    id = "gajkhgkagv",
                    title = "Dinner with Friends",
                    amount = 20,
                    type = TransactionTypeUI.EXPENSE,
                    formattedDate = "21 Jan 2020"
                ),
                MoneyTransaction(
                    id = "gajkhgkagv",
                    title = "Salary",
                    amount = 1000,
                    type = TransactionTypeUI.INCOME,
                    formattedDate = "21 Jan 2020"
                ),
                MoneyTransaction(
                    id = "gajkhgkagv",
                    title = "Keyboard",
                    amount = 100,
                    type = TransactionTypeUI.EXPENSE,
                    formattedDate = "21 Jan 2020"
                ),
                MoneyTransaction(
                    id = "gajkhgkagv",
                    title = "Dinner with Friends",
                    amount = 30,
                    type = TransactionTypeUI.EXPENSE,
                    formattedDate = "21 Jan 2020"
                ),
            )
        )
    }
}*/

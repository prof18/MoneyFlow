package data

import co.touchlab.stately.ensureNeverFrozen
import com.prof18.moneyflow.db.Transactions
import data.db.DatabaseSource
import domain.model.BalanceRecap
import domain.model.MoneyTransaction
import domain.model.TransactionTypeUI
import domain.repository.MoneyRepository
import kotlinx.coroutines.flow.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class MoneyRepositoryImpl(private val dbSource: DatabaseSource): MoneyRepository {

    private var allTransactions: Flow<List<Transactions>> = emptyFlow()

    init {
        ensureNeverFrozen()
        allTransactions = dbSource.selectAllTransaction()
    }

    override suspend fun refreshData() {
        allTransactions = dbSource.selectAllTransaction()
        val x = ""
    }

    // TODO: fix this
    override suspend fun getBalanceRecap(): Flow<BalanceRecap> = flow {
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

    override suspend fun getLatestTransactions(): Flow<List<MoneyTransaction>> {
        // TODO: map data correctly
        return allTransactions.map {
            it.map {transaction ->
                MoneyTransaction(
                    id = transaction.id,
                    title = transaction.description ?: "",
                    amount = transaction.amount,
                    type = TransactionTypeUI.EXPENSE,
                    formattedDate = "10/10/20"
                )
            }
        }.catch {
//            return@catch listOf()
            // TODO: some error handling?
        }
    }
}




package data

import InsertTransactionDTO
import co.touchlab.stately.ensureNeverFrozen
import com.prof18.moneyflow.db.CategoryTable
import com.prof18.moneyflow.db.TransactionTable
import data.db.DatabaseSource
import domain.model.BalanceRecap
import domain.model.Category
import domain.model.MoneyTransaction
import domain.model.TransactionTypeUI
import domain.repository.MoneyRepository
import kotlinx.coroutines.flow.*
import presentation.CategoryIcon

class MoneyRepositoryImpl(private val dbSource: DatabaseSource): MoneyRepository {

    private var allTransactions: Flow<List<TransactionTable>> = emptyFlow()
    private var allCategories: Flow<List<CategoryTable>> = emptyFlow()

    init {
        ensureNeverFrozen()
        allTransactions = dbSource.selectAllTransaction()
        allCategories = dbSource.selectAllCategories()
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
        }
    }

    override suspend fun insertTransaction(transactionDTO: InsertTransactionDTO) {
        dbSource.insertTransaction(transactionDTO)
    }

    override suspend fun getCategories(): Flow<List<Category>> {
        return allCategories.map {
            it.map {category ->
                Category(
                    id = category.id,
                    name = category.name,
                    icon = CategoryIcon.fromValue(category.iconName)
                )
            }
        }
    }
}




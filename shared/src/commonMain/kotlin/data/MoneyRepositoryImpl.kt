package data

import co.touchlab.stately.ensureNeverFrozen
import com.prof18.moneyflow.db.CategoryTable
import com.prof18.moneyflow.db.TransactionTable
import data.db.DatabaseSource
import data.db.model.InsertTransactionDTO
import data.db.model.TransactionType
import domain.entities.BalanceRecap
import domain.entities.Category
import domain.entities.MoneyTransaction
import domain.entities.TransactionTypeUI
import domain.mapper.mapToInsertTransactionDTO
import domain.repository.MoneyRepository
import kotlinx.coroutines.flow.*
import presentation.CategoryIcon
import presentation.addtransaction.TransactionToSave

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

    override suspend fun insertTransaction(transactionToSave: TransactionToSave) {
        dbSource.insertTransaction(transactionToSave.mapToInsertTransactionDTO())
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




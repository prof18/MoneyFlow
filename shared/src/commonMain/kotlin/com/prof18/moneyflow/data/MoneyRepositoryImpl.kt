package com.prof18.moneyflow.data

import com.prof18.moneyflow.data.db.DatabaseSource
import com.prof18.moneyflow.data.db.model.TransactionType
import com.prof18.moneyflow.db.AccountTable
import com.prof18.moneyflow.db.CategoryTable
import com.prof18.moneyflow.db.MonthlyRecapTable
import com.prof18.moneyflow.db.SelectLatestTransactions
import com.prof18.moneyflow.domain.entities.BalanceRecap
import com.prof18.moneyflow.domain.entities.Category
import com.prof18.moneyflow.domain.entities.MoneySummary
import com.prof18.moneyflow.domain.entities.MoneyTransaction
import com.prof18.moneyflow.domain.entities.TransactionTypeUI
import com.prof18.moneyflow.domain.repository.MoneyRepository
import com.prof18.moneyflow.presentation.addtransaction.TransactionToSave
import com.prof18.moneyflow.presentation.model.CategoryIcon
import com.prof18.moneyflow.utils.formatDateDayMonthYear
import com.prof18.moneyflow.utils.generateCurrentMonthId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlin.math.abs

internal class MoneyRepositoryImpl(
    private val dbSource: DatabaseSource,
) : MoneyRepository {

    private var allTransactions: Flow<List<SelectLatestTransactions>> = emptyFlow()
    private var allCategories: Flow<List<CategoryTable>> = emptyFlow()
    private var monthlyRecap: Flow<MonthlyRecapTable> = emptyFlow()
    private var account: Flow<AccountTable> = emptyFlow()

    init {
        allTransactions = dbSource.selectLatestTransactions().catch {
        }
        allCategories = dbSource.selectAllCategories()
        monthlyRecap = dbSource.selectCurrentMonthlyRecap()
        account = dbSource.selectCurrentAccount()
    }

    override fun getMoneySummary(): Flow<MoneySummary> {
        return getLatestTransactions().combine(getBalanceRecap()) { transactions, balanceRecap ->
            MoneySummary(
                balanceRecap = balanceRecap,
                latestTransactions = transactions,
            )
        }
    }

    override fun getBalanceRecap(): Flow<BalanceRecap> {
        return monthlyRecap.combine(account) { monthlyRecap: MonthlyRecapTable, account: AccountTable ->
            BalanceRecap(
                totalBalance = account.amount,
                monthlyIncome = monthlyRecap.incomeAmount,
                monthlyExpenses = monthlyRecap.outcomeAmount,
            )
        }
    }

    override fun getLatestTransactions(): Flow<List<MoneyTransaction>> {
        return allTransactions.map {
            it.map { transaction ->
                val transactionTypeUI = when (transaction.type) {
                    TransactionType.INCOME -> TransactionTypeUI.INCOME
                    TransactionType.OUTCOME -> TransactionTypeUI.EXPENSE
                }

                val transactionTitle = if (transaction.description.isNullOrEmpty()) {
                    transaction.categoryName
                } else {
                    transaction.description
                }

                MoneyTransaction(
                    id = transaction.id,
                    title = transactionTitle,
                    icon = CategoryIcon.fromValue(transaction.iconName),
                    amount = transaction.amount,
                    type = transactionTypeUI,
                    milliseconds = transaction.dateMillis,
                    formattedDate = transaction.dateMillis.formatDateDayMonthYear(),
                )
            }
        }
    }

    override suspend fun insertTransaction(transactionToSave: TransactionToSave) {

        val dateMillis = transactionToSave.dateMillis
        val transactionType = transactionToSave.transactionType
        var amount = transactionToSave.amount
        // From the UI, it arrives always positive, no matter what
        if (transactionType == TransactionType.OUTCOME) {
            amount = -amount
        }

        val description = transactionToSave.description
        val categoryId = transactionToSave.categoryId
        val monthId = dateMillis.generateCurrentMonthId()

        val monthlyRecap = dbSource.getMonthlyRecap(monthId)
        var monthlyIncomeAmount = monthlyRecap.incomeAmount
        var monthlyOutcomeAmount = monthlyRecap.outcomeAmount

        when (transactionType) {
            TransactionType.INCOME -> {
                monthlyIncomeAmount += amount
            }
            TransactionType.OUTCOME -> {
                // We keep the count positive. We know that it is an outcome
                monthlyOutcomeAmount += abs(amount)
            }
        }

        dbSource.insertTransaction(
            dateMillis,
            amount,
            description,
            categoryId,
            transactionType,
            monthId,
            monthlyIncomeAmount,
            monthlyOutcomeAmount,
        )
    }

    override suspend fun deleteTransaction(transactionId: Long) {

        val transaction = dbSource.getTransaction(transactionId)
        // It should not be null!
        if (transaction != null) {
            val transactionType = transaction.type
            val monthId = transaction.dateMillis.generateCurrentMonthId()
            var transactionAmountToUpdate = transaction.amount
            val monthlyRecap = dbSource.getMonthlyRecap(monthId)
            var monthlyIncomeAmount = monthlyRecap.incomeAmount
            var monthlyOutcomeAmount = monthlyRecap.outcomeAmount

            when (transactionType) {
                TransactionType.INCOME -> {
                    // Since it is an income, we need to subtract it from the amount
                    monthlyIncomeAmount -= transactionAmountToUpdate
                    transactionAmountToUpdate = -transactionAmountToUpdate
                }
                TransactionType.OUTCOME -> {
                    monthlyOutcomeAmount -= abs(transactionAmountToUpdate)
                    transactionAmountToUpdate = abs(transactionAmountToUpdate)
                }
            }
            dbSource.deleteTransaction(
                transactionId,
                transactionType,
                transactionAmountToUpdate,
                monthId,
                monthlyIncomeAmount,
                monthlyOutcomeAmount,
            )
        }
    }

    override fun getCategories(): Flow<List<Category>> {
        return allCategories.map {
            it.map { category ->
                Category(
                    id = category.id,
                    name = category.name,
                    icon = CategoryIcon.fromValue(category.iconName),
                )
            }
        }
    }

    override suspend fun getTransactionsPaginated(
        pageNum: Long,
        pageSize: Long,
    ): List<MoneyTransaction> {

        return dbSource.getTransactionsPaginated(
            pageNum = (pageNum * pageSize),
            pageSize = pageSize,
        )
            .map { transaction ->

                // TODO: return a different thing, to create date headers

                val transactionTypeUI = when (transaction.type) {
                    TransactionType.INCOME -> TransactionTypeUI.INCOME
                    TransactionType.OUTCOME -> TransactionTypeUI.EXPENSE
                }

                val transactionTitle = if (transaction.description.isNullOrEmpty()) {
                    transaction.categoryName
                } else {
                    transaction.description
                }

                MoneyTransaction(
                    id = transaction.id,
                    title = transactionTitle,
                    icon = CategoryIcon.fromValue(transaction.iconName),
                    amount = transaction.amount,
                    type = transactionTypeUI,
                    milliseconds = transaction.dateMillis,
                    formattedDate = transaction.dateMillis.formatDateDayMonthYear(),
                )
            }
    }
}

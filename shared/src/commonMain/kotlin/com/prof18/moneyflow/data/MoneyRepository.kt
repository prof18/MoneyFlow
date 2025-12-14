package com.prof18.moneyflow.data

import com.prof18.moneyflow.database.DatabaseHelper
import com.prof18.moneyflow.database.model.TransactionType
import com.prof18.moneyflow.domain.entities.BalanceRecap
import com.prof18.moneyflow.domain.entities.Category
import com.prof18.moneyflow.domain.entities.CurrencyConfig
import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.entities.MoneySummary
import com.prof18.moneyflow.domain.entities.MoneyTransaction
import com.prof18.moneyflow.domain.entities.TransactionTypeUI
import com.prof18.moneyflow.presentation.addtransaction.TransactionToSave
import com.prof18.moneyflow.presentation.model.CategoryIcon
import com.prof18.moneyflow.utils.currentMonthRange
import com.prof18.moneyflow.utils.formatDateDayMonthYear
import com.prof18.moneyflow.utils.logError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

import kotlin.math.abs

internal class MoneyRepository(
    private val dbSource: DatabaseHelper,
) {

    companion object {
        const val DEFAULT_PAGE_SIZE = 30L
        private const val LATEST_TRANSACTIONS_LIMIT = 10L
    }

    private val account = dbSource.selectDefaultAccount()

    private val currentMonthRange = currentMonthRange()

    private val allTransactions: Flow<List<com.prof18.moneyflow.db.SelectLatestTransactions>> =
        account.flatMapLatest { selectedAccount ->
            dbSource.selectLatestTransactions(
                accountId = selectedAccount.id,
                limit = LATEST_TRANSACTIONS_LIMIT,
            )
        }
            .catch { throwable ->
                val error = MoneyFlowError.GetAllTransaction(throwable)
                throwable.logError(error)
                emit(emptyList())
            }

    private val allCategories = dbSource.selectAllCategories()

    fun getMoneySummary(): Flow<MoneySummary> =
        combine(
            getLatestTransactions(),
            getBalanceRecap(),
            getCurrencyConfig(),
        ) { transactions, balanceRecap, currencyConfig ->
            MoneySummary(
                balanceRecap = balanceRecap,
                latestTransactions = transactions,
                currencyConfig = currencyConfig,
            )
        }

    fun getBalanceRecap(): Flow<BalanceRecap> {
        return account.flatMapLatest { selectedAccount ->
            val monthRange = currentMonthRange
            val recap = dbSource.selectMonthlyRecap(
                accountId = selectedAccount.id,
                monthStartMillis = monthRange.startMillis,
                monthEndMillis = monthRange.endMillis,
            )
            val balance = dbSource.selectAccountBalance(selectedAccount.id)
            combine(
                balance,
                recap,
            ) { totalBalanceCents, monthlyRecap ->
                BalanceRecap(
                    totalBalanceCents = totalBalanceCents,
                    monthlyIncomeCents = monthlyRecap.incomeCents ?: 0L,
                    monthlyExpensesCents = monthlyRecap.outcomeCents ?: 0L,
                )
            }
        }
    }

    fun getLatestTransactions(): Flow<List<MoneyTransaction>> {
        return allTransactions.map {
            it.map { transaction ->
                val transactionTypeUI = when (transaction.type) {
                    TransactionType.INCOME -> TransactionTypeUI.INCOME
                    TransactionType.OUTCOME -> TransactionTypeUI.EXPENSE
                }

                val transactionTitle = transaction.description.takeUnless { it.isNullOrEmpty() }
                    ?: transaction.categoryName

                MoneyTransaction(
                    id = transaction.id,
                    title = transactionTitle,
                    icon = CategoryIcon.fromValue(transaction.iconName),
                    amountCents = transaction.amountCents,
                    type = transactionTypeUI,
                    milliseconds = transaction.dateMillis,
                    formattedDate = transaction.dateMillis.formatDateDayMonthYear(),
                )
            }
        }
    }

    suspend fun insertTransaction(transactionToSave: TransactionToSave) {
        val accountId = account.first().id
        val amountCents = abs(transactionToSave.amountCents)
        dbSource.insertTransaction(
            accountId = accountId,
            dateMillis = transactionToSave.dateMillis,
            amountCents = amountCents,
            description = transactionToSave.description,
            categoryId = transactionToSave.categoryId,
            transactionType = transactionToSave.transactionType,
        )
    }

    suspend fun deleteTransaction(transactionId: Long) {
        val transaction = dbSource.getTransaction(transactionId) ?: return
        dbSource.deleteTransaction(transaction.id)
    }

    fun getCategories(): Flow<List<Category>> {
        return allCategories.map {
            it.map { category ->
                Category(
                    id = category.id,
                    name = category.name,
                    icon = CategoryIcon.fromValue(category.iconName),
                    type = category.type,
                    createdAtMillis = category.createdAtMillis,
                )
            }
        }
    }

    suspend fun getTransactionsPaginated(
        pageNum: Long,
        pageSize: Long,
    ): List<MoneyTransaction> {
        val accountId = account.first().id
        return dbSource.getTransactionsPaginated(
            accountId = accountId,
            pageNum = pageNum,
            pageSize = pageSize,
        )
            .map { transaction ->

                // TODO: return a different thing, to create date headers

                val transactionTypeUI = when (transaction.type) {
                    TransactionType.INCOME -> TransactionTypeUI.INCOME
                    TransactionType.OUTCOME -> TransactionTypeUI.EXPENSE
                }

                val transactionTitle = transaction.description.takeUnless { it.isNullOrEmpty() }
                    ?: transaction.categoryName

                MoneyTransaction(
                    id = transaction.id,
                    title = transactionTitle,
                    icon = CategoryIcon.fromValue(transaction.iconName),
                    amountCents = transaction.amountCents,
                    type = transactionTypeUI,
                    milliseconds = transaction.dateMillis,
                    formattedDate = transaction.dateMillis.formatDateDayMonthYear(),
                )
            }
    }

    fun getCurrencyConfig(): Flow<CurrencyConfig> =
        account.map { account ->
            CurrencyConfig(
                code = account.currencyCode,
                symbol = account.currencySymbol,
                decimalPlaces = account.currencyDecimalPlaces.toInt(),
            )
        }

    suspend fun addCategory(
        name: String,
        type: TransactionType,
        iconName: String,
    ) {
        dbSource.insertCategory(
            name = name,
            type = type,
            iconName = iconName,
            isSystem = false,
        )
    }

    suspend fun updateCategory(category: Category) {
        dbSource.updateCategory(
            id = category.id,
            name = category.name,
            iconName = category.icon.iconName,
        )
    }
}

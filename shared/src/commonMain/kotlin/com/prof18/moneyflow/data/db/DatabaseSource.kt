package com.prof18.moneyflow.data.db

import com.prof18.moneyflow.data.db.model.TransactionType
import com.prof18.moneyflow.db.AccountTable
import com.prof18.moneyflow.db.CategoryTable
import com.prof18.moneyflow.db.MonthlyRecapTable
import com.prof18.moneyflow.db.SelectLatestTransactions
import com.prof18.moneyflow.db.SelectTransactionsPaginated
import com.prof18.moneyflow.db.TransactionTable
import com.prof18.moneyflow.utils.CurrentMonthID
import com.prof18.moneyflow.utils.MillisSinceEpoch
import kotlinx.coroutines.flow.Flow

internal interface DatabaseSource {
    fun selectLatestTransactions(): Flow<List<SelectLatestTransactions>>
    fun selectAllCategories(): Flow<List<CategoryTable>>
    fun selectCurrentMonthlyRecap(): Flow<MonthlyRecapTable>
    fun selectCurrentAccount(): Flow<AccountTable>
    suspend fun insertTransaction(
        dateMillis: MillisSinceEpoch,
        amount: Double, // Already with negative or positive sign
        description: String?,
        categoryId: Long,
        transactionType: TransactionType,
        monthId: CurrentMonthID,
        monthlyIncomeAmount: Double,
        monthlyOutcomeAmount: Double,
    )
    suspend fun deleteTransaction(
        transactionId: Long,
        transactionType: TransactionType,
        transactionAmountToUpdate: Double,
        monthId: Long,
        monthlyIncomeAmount: Double,
        monthlyOutcomeAmount: Double,
    )
    suspend fun getMonthlyRecap(currentMonthID: CurrentMonthID): MonthlyRecapTable
    suspend fun getTransaction(transactionId: Long): TransactionTable?
    suspend fun getTransactionsPaginated(pageNum: Long, pageSize: Long): List<SelectTransactionsPaginated>
}

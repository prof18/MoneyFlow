package com.prof18.moneyflow.data

import DataFactory
import app.cash.turbine.test
import com.prof18.moneyflow.database.DatabaseHelper
import com.prof18.moneyflow.database.default.defaultCategories
import com.prof18.moneyflow.database.model.TransactionType
import com.prof18.moneyflow.presentation.addtransaction.TransactionToSave
import com.prof18.moneyflow.utilities.closeDriver
import com.prof18.moneyflow.utilities.createDriver
import com.prof18.moneyflow.utilities.getDatabaseHelper
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MoneyRepositoryImplTest {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var moneyRepository: MoneyRepository

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        createDriver()
        databaseHelper = getDatabaseHelper()
        moneyRepository = MoneyRepository(databaseHelper)
    }

    @AfterTest
    fun tearDown() {
        closeDriver()
    }

    @Test
    fun getBalanceRecap_UpdatesDataWhenAddingIncomeAndNoDataPresent() = runTest(testDispatcher) {
        moneyRepository.getBalanceRecap().test {
            assertEquals(DataFactory.getEmptyBalanceRecap(), awaitItem())
        }

        moneyRepository.insertTransaction(
            TransactionToSave(
                dateMillis = Clock.System.now().toEpochMilliseconds(),
                amount = 10.0,
                description = null,
                categoryId = 0,
                transactionType = TransactionType.OUTCOME,
            ),
        )

        moneyRepository.getBalanceRecap().test {
            val balance = awaitItem()
            assertEquals(-10.0, balance.totalBalance)
            assertEquals(10.0, balance.monthlyExpenses)
            assertEquals(0.0, balance.monthlyIncome)
        }
    }

    @Test
    fun getBalanceRecap_UpdatesDataCorrectlyWhenAddingIncome() = runTest(testDispatcher) {

        // Setup
        moneyRepository.insertTransaction(
            TransactionToSave(
                dateMillis = Clock.System.now().toEpochMilliseconds() - DataFactory.dayMillis,
                amount = 10.0,
                description = null,
                categoryId = 0,
                transactionType = TransactionType.OUTCOME,
            ),
        )

        moneyRepository.insertTransaction(
            TransactionToSave(
                dateMillis = Clock.System.now().toEpochMilliseconds(),
                amount = 10.0,
                description = null,
                categoryId = 0,
                transactionType = TransactionType.OUTCOME,
            ),
        )

        // Act
        moneyRepository.insertTransaction(
            TransactionToSave(
                dateMillis = Clock.System.now().toEpochMilliseconds(),
                amount = 20.0,
                description = null,
                categoryId = 0,
                transactionType = TransactionType.INCOME,
            ),
        )

        // Assert
        moneyRepository.getBalanceRecap().test {
            val balance = awaitItem()
            assertEquals(0.0, balance.totalBalance)
            assertEquals(20.0, balance.monthlyExpenses)
            assertEquals(20.0, balance.monthlyIncome)
        }
    }

    @Test
    fun getBalanceRecap_UpdatesDataCorrectlyWhenAddingOutcome() = runTest(testDispatcher) {
        // Setup
        moneyRepository.insertTransaction(
            TransactionToSave(
                dateMillis = Clock.System.now().toEpochMilliseconds(),
                amount = 10.0,
                description = null,
                categoryId = 0,
                transactionType = TransactionType.OUTCOME,
            ),
        )

        moneyRepository.insertTransaction(
            TransactionToSave(
                dateMillis = Clock.System.now().toEpochMilliseconds(),
                amount = 10.0,
                description = null,
                categoryId = 0,
                transactionType = TransactionType.INCOME,
            ),
        )

        // Act
        moneyRepository.insertTransaction(
            TransactionToSave(
                dateMillis = Clock.System.now().toEpochMilliseconds(),
                amount = 20.0,
                description = null,
                categoryId = 0,
                transactionType = TransactionType.OUTCOME,
            ),
        )

        // Assert
        moneyRepository.getBalanceRecap().test {
            val balance = awaitItem()
            assertEquals(-20.0, balance.totalBalance)
            assertEquals(30.0, balance.monthlyExpenses)
            assertEquals(10.0, balance.monthlyIncome)
        }
    }

    @Test
    fun getLatestTransactions_returnsCategoryIfDescriptionIsNull() = runTest(testDispatcher) {

        database.transactionTableQueries.insertTransaction(
            dateMillis = Clock.System.now().toEpochMilliseconds(),
            amount = 10.0,
            description = null,
            categoryId = 1,
            type = TransactionType.INCOME,
        )

        moneyRepository.getLatestTransactions().test {
            val transactions = awaitItem()
            print(transactions)
            assertEquals(defaultCategories.first().name, transactions.first().title)
        }
    }

    @Test
    fun getLatestTransactions_returnsDescriptionIfDescriptionIsNotNull() = runTest(testDispatcher) {

        database.transactionTableQueries.insertTransaction(
            dateMillis = Clock.System.now().toEpochMilliseconds(),
            amount = 10.0,
            description = "Description",
            categoryId = 1,
            type = TransactionType.INCOME,
        )

        moneyRepository.getLatestTransactions().test {
            val transactions = awaitItem()
            print(transactions)
            assertEquals("Description", transactions.first().title)
        }
    }

    @Test
    fun deleteTransaction_updatesBalanceCorrectlyWhenRemovingOutcome() = runTest(testDispatcher) {

        // Setup
        moneyRepository.insertTransaction(
            TransactionToSave(
                dateMillis = Clock.System.now().toEpochMilliseconds(),
                amount = 10.0,
                description = null,
                categoryId = 0,
                transactionType = TransactionType.OUTCOME,
            ),
        )

        moneyRepository.insertTransaction(
            TransactionToSave(
                dateMillis = Clock.System.now().toEpochMilliseconds(),
                amount = 10.0,
                description = null,
                categoryId = 0,
                transactionType = TransactionType.INCOME,
            ),
        )

        moneyRepository.deleteTransaction(1)

        moneyRepository.getBalanceRecap().test {
            val balance = awaitItem()
            assertEquals(10.0, balance.totalBalance)
            assertEquals(0.0, balance.monthlyExpenses)
            assertEquals(10.0, balance.monthlyIncome)
        }
    }

    @Test
    fun deleteTransaction_updatesBalanceCorrectlyWhenRemovingIncome() = runTest(testDispatcher) {

        // Setup
        moneyRepository.insertTransaction(
            TransactionToSave(
                dateMillis = Clock.System.now().toEpochMilliseconds(),
                amount = 10.0,
                description = null,
                categoryId = 0,
                transactionType = TransactionType.OUTCOME,
            ),
        )

        moneyRepository.insertTransaction(
            TransactionToSave(
                dateMillis = Clock.System.now().toEpochMilliseconds(),
                amount = 10.0,
                description = null,
                categoryId = 0,
                transactionType = TransactionType.INCOME,
            ),
        )

        moneyRepository.getBalanceRecap().test {
            val balance = awaitItem()
            assertEquals(0.0, balance.totalBalance)
            assertEquals(10.0, balance.monthlyExpenses)
            assertEquals(10.0, balance.monthlyIncome)
        }

        moneyRepository.deleteTransaction(2)

        moneyRepository.getBalanceRecap().test {
            val balance = awaitItem()
            assertEquals(-10.0, balance.totalBalance)
            assertEquals(10.0, balance.monthlyExpenses)
            assertEquals(0.0, balance.monthlyIncome)
        }
    }
}

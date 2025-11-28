package com.prof18.moneyflow

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.prof18.moneyflow.database.model.TransactionType
import com.prof18.moneyflow.presentation.addtransaction.AddTransactionScreen
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AddTransactionRoborazziTest : RoborazziTestBase() {

    @Test
    fun captureAddTransactionScreen() {
        composeRule.setContent {
            MoneyFlowTheme {
                AddTransactionScreen(
                    categoryState = remember { mutableStateOf(RoborazziSampleData.sampleCategory) },
                    navigateUp = {},
                    navigateToCategoryList = {},
                    addTransaction = {},
                    amountText = "10.00",
                    updateAmountText = {},
                    descriptionText = "Pizza 🍕",
                    updateDescriptionText = {},
                    selectedTransactionType = TransactionType.OUTCOME,
                    updateTransactionType = {},
                    updateYear = {},
                    updateMonth = {},
                    updateDay = {},
                    saveDate = {},
                    dateLabel = "11 July 2021",
                    addTransactionAction = null,
                    resetAction = {},
                )
            }
        }

        capture("add_transaction_screen")
    }
}

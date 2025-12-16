package com.prof18.moneyflow

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.prof18.moneyflow.database.model.TransactionType
import com.prof18.moneyflow.presentation.addtransaction.AddTransactionScreen
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import kotlin.time.Clock

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(
    sdk = [33],
    qualifiers = RobolectricDeviceQualifiers.Pixel7Pro,
)
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
                    updateSelectedDate = {},
                    dateLabel = "11 July 2021",
                    selectedDateMillis = Clock.System.now().toEpochMilliseconds(),
                    addTransactionAction = null,
                    resetAction = {},
                    currencyConfig = RoborazziSampleData.sampleCurrencyConfig,
                )
            }
        }

        capture("add_transaction_screen")
    }

    @Test
    fun captureAddTransactionDatePicker() {
        val dateLabel = "11 July 2021"

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
                    updateSelectedDate = {},
                    dateLabel = dateLabel,
                    selectedDateMillis = Clock.System.now().toEpochMilliseconds(),
                    addTransactionAction = null,
                    resetAction = {},
                    currencyConfig = RoborazziSampleData.sampleCurrencyConfig,
                )
            }
        }

        composeRule.onNodeWithText(dateLabel).performClick()

        capture("add_transaction_screen_date_picker")
    }
}

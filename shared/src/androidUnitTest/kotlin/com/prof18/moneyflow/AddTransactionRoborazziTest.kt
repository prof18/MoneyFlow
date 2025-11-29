package com.prof18.moneyflow

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material.Scaffold
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.prof18.moneyflow.database.model.TransactionType
import com.prof18.moneyflow.presentation.addtransaction.AddTransactionScreen
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(
    sdk = [33],
    qualifiers = RobolectricDeviceQualifiers.Pixel7,
)
class AddTransactionRoborazziTest : RoborazziTestBase() {

    @Test
    fun captureAddTransactionScreen() {
        composeRule.setContent {
            MoneyFlowTheme {
                Scaffold {
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
                        paddingValues = it,
                    )
                }
            }
        }

        capture("add_transaction_screen")
    }
}

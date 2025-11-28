package com.prof18.moneyflow

import com.prof18.moneyflow.features.alltransactions.AllTransactionsUiState
import com.prof18.moneyflow.presentation.alltransactions.AllTransactionsScreen
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AllTransactionsRoborazziTest : RoborazziTestBase() {

    @Test
    fun captureAllTransactionsScreen() {
        composeRule.setContent {
            MoneyFlowTheme {
                AllTransactionsScreen(
                    stateFlow = MutableStateFlow(
                        AllTransactionsUiState(
                            transactions = RoborazziSampleData.sampleTransactions,
                        ),
                    ),
                    loadNextPage = {},
                )
            }
        }

        capture("all_transactions_screen")
    }
}

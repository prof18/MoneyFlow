package com.prof18.moneyflow

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.prof18.moneyflow.features.alltransactions.AllTransactionsUiState
import com.prof18.moneyflow.presentation.alltransactions.AllTransactionsScreen
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(
    sdk = [33],
    qualifiers = RobolectricDeviceQualifiers.Pixel7Pro,
)
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

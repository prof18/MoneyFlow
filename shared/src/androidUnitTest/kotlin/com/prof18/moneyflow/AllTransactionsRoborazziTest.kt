package com.prof18.moneyflow

import androidx.compose.material3.Scaffold
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.prof18.moneyflow.features.alltransactions.AllTransactionsUiState
import com.prof18.moneyflow.presentation.alltransactions.AllTransactionsScreen
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers

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
                Scaffold {
                    AllTransactionsScreen(
                        stateFlow = MutableStateFlow(
                            AllTransactionsUiState(
                                transactions = RoborazziSampleData.sampleTransactions,
                            ),
                        ),
                        loadNextPage = {},
                        paddingValues = it,
                    )
                }
            }
        }

        capture("all_transactions_screen")
    }
}

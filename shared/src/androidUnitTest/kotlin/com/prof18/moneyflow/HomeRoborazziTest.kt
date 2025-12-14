package com.prof18.moneyflow

import androidx.compose.material3.Scaffold
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.prof18.moneyflow.domain.entities.BalanceRecap
import com.prof18.moneyflow.presentation.home.HomeModel
import com.prof18.moneyflow.presentation.home.HomeScreen
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
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
class HomeRoborazziTest : RoborazziTestBase() {

    @Test
    fun captureHomeScreen() {
        composeRule.setContent {
            MoneyFlowTheme {
                Scaffold {
                    HomeScreen(
                        homeModel = HomeModel.HomeState(
                            balanceRecap = BalanceRecap(
                                totalBalanceCents = 500_000,
                                monthlyIncomeCents = 100_000,
                                monthlyExpensesCents = 50_00,
                            ),
                            latestTransactions = RoborazziSampleData.sampleTransactions,
                            currencyConfig = RoborazziSampleData.sampleCurrencyConfig,
                        ),
                        hideSensitiveDataState = false,
                        navigateToAllTransactions = {},
                        paddingValues = it,
                    )
                }
            }
        }

        capture("home_screen")
    }
}

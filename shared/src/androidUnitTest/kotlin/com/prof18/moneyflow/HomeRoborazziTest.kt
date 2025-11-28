package com.prof18.moneyflow

import androidx.test.ext.junit.runners.AndroidJUnit4
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
@Config(sdk = [33])
class HomeRoborazziTest : RoborazziTestBase() {

    @Test
    fun captureHomeScreen() {
        composeRule.setContent {
            MoneyFlowTheme {
                HomeScreen(
                    homeModel = HomeModel.HomeState(
                        balanceRecap = BalanceRecap(
                            totalBalance = 5000.0,
                            monthlyIncome = 1000.0,
                            monthlyExpenses = 50.0,
                        ),
                        latestTransactions = RoborazziSampleData.sampleTransactions,
                    ),
                    hideSensitiveDataState = false,
                    navigateToAllTransactions = {},
                )
            }
        }

        capture("home_screen")
    }
}

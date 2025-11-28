package com.prof18.moneyflow

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.prof18.moneyflow.presentation.budget.BudgetScreen
import com.prof18.moneyflow.presentation.recap.RecapScreen
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [33])
class BudgetAndRecapRoborazziTest : RoborazziTestBase() {

    @Test
    fun captureBudgetScreen() {
        composeRule.setContent {
            MoneyFlowTheme {
                BudgetScreen()
            }
        }
        capture("budget_screen")
    }

    @Test
    fun captureRecapScreen() {
        composeRule.setContent {
            MoneyFlowTheme {
                RecapScreen()
            }
        }
        capture("recap_screen")
    }
}

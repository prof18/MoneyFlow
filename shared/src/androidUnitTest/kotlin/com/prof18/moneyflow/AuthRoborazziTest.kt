package com.prof18.moneyflow

import com.prof18.moneyflow.presentation.auth.AuthScreen
import com.prof18.moneyflow.presentation.auth.AuthState
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AuthRoborazziTest : RoborazziTestBase() {

    @Test
    fun captureAuthScreen() {
        composeRule.setContent {
            MoneyFlowTheme {
                AuthScreen(
                    authState = AuthState.AUTH_IN_PROGRESS,
                    onRetryClick = {},
                )
            }
        }

        capture("auth_screen")
    }
}

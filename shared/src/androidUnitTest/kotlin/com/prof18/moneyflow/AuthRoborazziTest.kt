package com.prof18.moneyflow

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.prof18.moneyflow.presentation.auth.AuthScreen
import com.prof18.moneyflow.presentation.auth.AuthState
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

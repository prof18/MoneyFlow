package com.prof18.moneyflow

import com.prof18.moneyflow.features.settings.BiometricAvailabilityChecker
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.prof18.moneyflow.presentation.settings.SettingsScreen
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [33])
class SettingsRoborazziTest : RoborazziTestBase() {

    @Test
    fun captureSettingsScreen() {
        composeRule.setContent {
            MoneyFlowTheme {
                SettingsScreen(
                    biometricAvailabilityChecker = object : BiometricAvailabilityChecker {
                        override fun isBiometricSupported(): Boolean = true
                    },
                    biometricState = true,
                    onBiometricEnabled = {},
                    hideSensitiveDataState = true,
                    onHideSensitiveDataEnabled = {},
                )
            }
        }

        capture("settings_screen")
    }
}

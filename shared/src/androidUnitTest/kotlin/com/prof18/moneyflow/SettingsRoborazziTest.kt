package com.prof18.moneyflow

import androidx.compose.material3.Scaffold
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.prof18.moneyflow.features.settings.BiometricAvailabilityChecker
import com.prof18.moneyflow.presentation.settings.SettingsScreen
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
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
class SettingsRoborazziTest : RoborazziTestBase() {

    @Test
    fun captureSettingsScreen() {
        composeRule.setContent {
            MoneyFlowTheme {
                Scaffold {
                    SettingsScreen(
                        biometricAvailabilityChecker = object : BiometricAvailabilityChecker {
                            override fun isBiometricSupported(): Boolean = true
                        },
                        biometricState = true,
                        onBiometricEnabled = {},
                        hideSensitiveDataState = true,
                        onHideSensitiveDataEnabled = {},
                        paddingValues = it,
                    )
                }
            }
        }

        capture("settings_screen")
    }
}

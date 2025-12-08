package com.prof18.moneyflow

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.RoborazziRule
import com.prof18.moneyflow.data.MoneyRepository
import com.prof18.moneyflow.data.SettingsRepository
import com.prof18.moneyflow.data.settings.SettingsSource
import com.prof18.moneyflow.features.addtransaction.AddTransactionViewModel
import com.prof18.moneyflow.features.alltransactions.AllTransactionsViewModel
import com.prof18.moneyflow.features.categories.CategoriesViewModel
import com.prof18.moneyflow.features.home.HomeViewModel
import com.prof18.moneyflow.features.settings.BiometricAvailabilityChecker
import com.prof18.moneyflow.features.settings.SettingsViewModel
import com.prof18.moneyflow.navigation.MoneyFlowNavHost
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import com.prof18.moneyflow.utilities.closeDriver
import com.prof18.moneyflow.utilities.createDriver
import com.prof18.moneyflow.utilities.getDatabaseHelper
import com.russhwolf.settings.MapSettings
import com.russhwolf.settings.Settings
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(
    sdk = [33],
    qualifiers = RobolectricDeviceQualifiers.Pixel7Pro,
)
class MoneyFlowNavHostRoborazziTest : RoborazziTestBase() {

    @Before
    fun setup() {
        createDriver()
        stopKoin() // Ensure Koin is stopped before starting
        startKoin {
            modules(
                module {
                    single { getDatabaseHelper() }
                    single<Settings> { MapSettings() }
                    single { SettingsSource(get()) }
                    single { SettingsRepository(get()) }
                    single { MoneyRepository(get()) }
                    single { MoneyFlowErrorMapper() }
                    single<BiometricAvailabilityChecker> {
                        object : BiometricAvailabilityChecker {
                            override fun isBiometricSupported(): Boolean = true
                        }
                    }
                    viewModel { HomeViewModel(get(), get(), get()) }
                    viewModel { AddTransactionViewModel(get(), get()) }
                    viewModel { CategoriesViewModel(get(), get()) }
                    viewModel { AllTransactionsViewModel(get(), get()) }
                    viewModel { SettingsViewModel(get()) }
                }
            )
        }
    }

    @After
    fun teardownResources() {
        stopKoin()
        closeDriver()
    }

    @Test
    fun captureMoneyFlowNavHost() {
        composeRule.setContent {
            MoneyFlowTheme {
                MoneyFlowNavHost()
            }
        }
        capture("money_flow_nav_host")
    }
}

package com.prof18.moneyflow

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.junit4.createComposeRule
import com.prof18.moneyflow.domain.entities.BalanceRecap
import com.prof18.moneyflow.domain.entities.Category
import com.prof18.moneyflow.domain.entities.MoneyTransaction
import com.prof18.moneyflow.domain.entities.TransactionTypeUI
import com.prof18.moneyflow.database.model.TransactionType
import com.prof18.moneyflow.features.alltransactions.AllTransactionsUiState
import com.prof18.moneyflow.features.settings.BiometricAvailabilityChecker
import com.prof18.moneyflow.presentation.addtransaction.AddTransactionScreen
import com.prof18.moneyflow.presentation.alltransactions.AllTransactionsScreen
import com.prof18.moneyflow.presentation.auth.AuthScreen
import com.prof18.moneyflow.presentation.auth.AuthState
import com.prof18.moneyflow.presentation.categories.CategoriesScreen
import com.prof18.moneyflow.presentation.categories.CategoryModel
import com.prof18.moneyflow.presentation.categories.data.CategoryUIData
import com.prof18.moneyflow.presentation.home.HomeModel
import com.prof18.moneyflow.presentation.home.HomeScreen
import com.prof18.moneyflow.presentation.model.CategoryIcon
import com.prof18.moneyflow.presentation.model.UIErrorMessage
import com.prof18.moneyflow.presentation.recap.RecapScreen
import com.prof18.moneyflow.presentation.settings.SettingsScreen
import com.prof18.moneyflow.presentation.budget.BudgetScreen
import com.prof18.moneyflow.ui.components.MFTopBar
import com.prof18.moneyflow.ui.components.TransactionCard
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import io.github.takahirom.roborazzi.captureRoboImage
import kotlinx.coroutines.flow.MutableStateFlow
import money_flow.shared.generated.resources.Res
import money_flow.shared.generated.resources.error_get_categories_message
import money_flow.shared.generated.resources.error_nerd_message
import money_flow.shared.generated.resources.settings_screen
import org.jetbrains.compose.resources.stringResource
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.File

@RunWith(RobolectricTestRunner::class)
class RoborazziScreenshotsTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val snapshotDir: File = File(
        System.getProperty("roborazzi.test.record.dir")
            ?: error("Missing roborazzi.test.record.dir property"),
    ).also { directory ->
        directory.mkdirs()
    }

    private fun capture(name: String) {
        val target = snapshotDir.resolve("$name.png")
        composeRule.waitForIdle()
        composeRule.onRoot().captureRoboImage(target.path)
    }

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
                        latestTransactions = sampleTransactions,
                    ),
                    hideSensitiveDataState = false,
                    navigateToAllTransactions = {},
                )
            }
        }

        capture("home_screen")
    }

    @Test
    fun captureAllTransactionsScreen() {
        composeRule.setContent {
            MoneyFlowTheme {
                AllTransactionsScreen(
                    stateFlow = MutableStateFlow(
                        AllTransactionsUiState(
                            transactions = sampleTransactions,
                        ),
                    ),
                    loadNextPage = {},
                )
            }
        }

        capture("all_transactions_screen")
    }

    @Test
    fun captureAddTransactionScreen() {
        composeRule.setContent {
            MoneyFlowTheme {
                AddTransactionScreen(
                    categoryState = remember { mutableStateOf(sampleCategory) },
                    navigateUp = {},
                    navigateToCategoryList = {},
                    addTransaction = {},
                    amountText = "10.00",
                    updateAmountText = {},
                    descriptionText = "Pizza 🍕",
                    updateDescriptionText = {},
                    selectedTransactionType = TransactionType.OUTCOME,
                    updateTransactionType = {},
                    updateYear = {},
                    updateMonth = {},
                    updateDay = {},
                    saveDate = {},
                    dateLabel = "11 July 2021",
                    addTransactionAction = null,
                    resetAction = {},
                )
            }
        }

        capture("add_transaction_screen")
    }

    @Test
    fun captureCategoriesScreen() {
        composeRule.setContent {
            MoneyFlowTheme {
                CategoriesScreen(
                    navigateUp = {},
                    sendCategoryBack = {},
                    isFromAddTransaction = true,
                    categoryModel = CategoryModel.CategoryState(
                        categories = listOf(
                            Category(
                                id = 0,
                                name = "Food",
                                icon = CategoryIcon.IC_HAMBURGER_SOLID,
                            ),
                            Category(
                                id = 1,
                                name = "Drinks",
                                icon = CategoryIcon.IC_COCKTAIL_SOLID,
                            ),
                        ),
                    ),
                )
            }
        }

        capture("categories_screen")
    }

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

    @Test
    fun captureBudgetAndRecapScreens() {
        composeRule.setContent {
            MoneyFlowTheme {
                BudgetScreen()
            }
        }
        capture("budget_screen")

        composeRule.setContent {
            MoneyFlowTheme {
                RecapScreen()
            }
        }
        capture("recap_screen")
    }

    @Test
    fun captureKeyComponents() {
        composeRule.setContent {
            MoneyFlowTheme {
                MFTopBar(
                    topAppBarText = stringResource(Res.string.settings_screen),
                    actionTitle = "Save",
                    onBackPressed = {},
                    onActionClicked = {},
                    actionEnabled = false,
                )
            }
        }
        capture("component_top_bar")

        composeRule.setContent {
            MoneyFlowTheme {
                TransactionCard(
                    transaction = sampleTransactions.first(),
                    onLongPress = {},
                    onClick = {},
                    hideSensitiveData = true,
                )
            }
        }
        capture("component_transaction_card")

        composeRule.setContent {
            MoneyFlowTheme {
                com.prof18.moneyflow.ui.components.ErrorView(
                    uiErrorMessage = UIErrorMessage(
                        message = Res.string.error_get_categories_message,
                        messageKey = "error_get_categories_message",
                        nerdMessage = Res.string.error_nerd_message,
                        nerdMessageKey = "error_nerd_message",
                        nerdMessageArgs = listOf("101"),
                    ),
                )
            }
        }
        capture("component_error_view")
    }

    private val sampleCategory = CategoryUIData(
        id = 1,
        name = "Food",
        icon = CategoryIcon.IC_HAMBURGER_SOLID,
    )

    private val sampleTransactions = listOf(
        MoneyTransaction(
            id = 0,
            title = "Ice Cream",
            icon = CategoryIcon.IC_ICE_CREAM_SOLID,
            amount = 10.0,
            type = TransactionTypeUI.EXPENSE,
            milliseconds = 0,
            formattedDate = "12 July 2021",
        ),
        MoneyTransaction(
            id = 1,
            title = "Tip",
            icon = CategoryIcon.IC_MONEY_CHECK_ALT_SOLID,
            amount = 50.0,
            type = TransactionTypeUI.INCOME,
            milliseconds = 0,
            formattedDate = "12 July 2021",
        ),
    )
}

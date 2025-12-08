package com.prof18.moneyflow

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.prof18.moneyflow.presentation.model.UIErrorMessage
import com.prof18.moneyflow.ui.components.MFTopBar
import com.prof18.moneyflow.ui.components.TransactionCard
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import money_flow.shared.generated.resources.Res
import money_flow.shared.generated.resources.error_get_categories_message
import money_flow.shared.generated.resources.error_nerd_message
import money_flow.shared.generated.resources.settings_screen
import org.jetbrains.compose.resources.stringResource
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
class ComponentsRoborazziTest : RoborazziTestBase() {

    @Test
    fun captureTopBar() {
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
    }

    @Test
    fun captureTransactionCard() {
        composeRule.setContent {
            MoneyFlowTheme {
                TransactionCard(
                    transaction = RoborazziSampleData.sampleTransactions.first(),
                    onLongPress = {},
                    onClick = {},
                    hideSensitiveData = true,
                )
            }
        }
        capture("component_transaction_card")
    }

    @Test
    fun captureErrorView() {
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
}

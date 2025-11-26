package com.prof18.moneyflow.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.prof18.moneyflow.presentation.model.UIErrorMessage
import com.prof18.moneyflow.ui.style.Margins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import money_flow.shared.generated.resources.Res
import money_flow.shared.generated.resources.error_add_transaction_message
import money_flow.shared.generated.resources.error_nerd_message
import money_flow.shared.generated.resources.shrug
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Suppress("SpreadOperator")
@Composable
internal fun ErrorView(
    uiErrorMessage: UIErrorMessage,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Margins.regular),
        ) {
            Text(
                text = stringResource(Res.string.shrug),
                style = MaterialTheme.typography.caption,
            )

            Text(
                text = stringResource(
                    uiErrorMessage.message,
                    *uiErrorMessage.messageArgs.toTypedArray(),
                ),
                style = MaterialTheme.typography.body1,
            )
            if (uiErrorMessage.nerdMessageArgs.isNotEmpty()) {
                val nerdMessage = stringResource(
                    uiErrorMessage.nerdMessage,
                    *uiErrorMessage.nerdMessageArgs.toTypedArray(),
                )
                if (nerdMessage.isNotBlank()) {
                    Text(
                        text = nerdMessage,
                        style = MaterialTheme.typography.caption,
                    )
                }
            }
        }
    }
}

@Preview(name = "ErrorView Light")
@Composable
private fun ErrorViewPreview() {
    val message = UIErrorMessage(
        message = Res.string.error_add_transaction_message,
        messageKey = "error_add_transaction_message",
        nerdMessage = Res.string.error_nerd_message,
        nerdMessageKey = "error_nerd_message",
        nerdMessageArgs = listOf("101"),
    )

    Surface {
        MoneyFlowTheme {
            ErrorView(uiErrorMessage = message)
        }
    }
}

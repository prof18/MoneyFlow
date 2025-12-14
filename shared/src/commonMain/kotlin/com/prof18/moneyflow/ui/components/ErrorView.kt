package com.prof18.moneyflow.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.prof18.moneyflow.presentation.model.UIErrorMessage
import com.prof18.moneyflow.ui.style.Margins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import money_flow.shared.generated.resources.Res
import money_flow.shared.generated.resources.error_add_transaction_message
import money_flow.shared.generated.resources.shrug
import org.jetbrains.compose.resources.stringResource

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
                style = MaterialTheme.typography.bodySmall,
            )

            Text(
                text = stringResource(uiErrorMessage.message),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Preview(name = "ErrorView Light")
@Composable
private fun ErrorViewPreview() {
    val message = UIErrorMessage(
        message = Res.string.error_add_transaction_message,
    )

    Surface {
        MoneyFlowTheme {
            ErrorView(uiErrorMessage = message)
        }
    }
}

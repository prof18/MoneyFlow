package com.prof18.moneyflow.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.prof18.moneyflow.R
import com.prof18.moneyflow.presentation.model.UIErrorMessage
import com.prof18.moneyflow.ui.style.AppMargins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme

@Composable
fun ErrorView(
    uiErrorMessage: UIErrorMessage
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppMargins.regular)
        ) {

            Text(
                text = stringResource(id = R.string.shrug),
                style = MaterialTheme.typography.caption,
            )

            Text(
                text = uiErrorMessage.message,
                style = MaterialTheme.typography.body1,
            )
            Text(
                text = uiErrorMessage.nerdMessage,
                style = MaterialTheme.typography.caption,
            )
        }
    }
}

@Preview
@Composable
fun ErrorViewLightPreview() {
    val message = UIErrorMessage(message = "New error occurred", nerdMessage = "Error code: 101")

    Surface {
        MoneyFlowTheme {
            ErrorView(uiErrorMessage = message)
        }
    }
}

@Preview
@Composable
fun ErrorViewDarkPreview() {
    val message = UIErrorMessage(message = "New error occurred", nerdMessage = "Error code: 101")

    Surface {
        MoneyFlowTheme(darkTheme = true) {
            ErrorView(uiErrorMessage = message)
        }
    }
}
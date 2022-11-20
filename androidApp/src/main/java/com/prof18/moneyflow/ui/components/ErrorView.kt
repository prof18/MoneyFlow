package com.prof18.moneyflow.ui.components

import android.content.res.Configuration
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.prof18.moneyflow.R
import com.prof18.moneyflow.presentation.model.UIErrorMessage
import com.prof18.moneyflow.ui.style.Margins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme

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

@Preview(name = "ErrorView Light")
@Preview(name = "ErrorView Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ErrorViewPreview() {
    val message = UIErrorMessage(message = "New error occurred", nerdMessage = "Error code: 101")

    Surface {
        MoneyFlowTheme {
            ErrorView(uiErrorMessage = message)
        }
    }
}

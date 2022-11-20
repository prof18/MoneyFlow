package com.prof18.moneyflow.features.auth

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.prof18.moneyflow.R
import com.prof18.moneyflow.ui.style.Margins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme

@Composable
internal fun AuthScreen(
    authState: AuthState,
    onRetryClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (authState == AuthState.AUTH_IN_PROGRESS) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.size(Margins.regular))
        }

        Text(
            text = authState.getAuthMessage(),
            style = MaterialTheme.typography.body1,
        )

        if (authState == AuthState.AUTH_ERROR) {
            Spacer(modifier = Modifier.size(Margins.regular))
            Button(onClick = { onRetryClick() }) {
                Text(
                    text = stringResource(R.string.retry),
                    style = MaterialTheme.typography.button,
                )
            }
        }
    }
}

@Composable
private fun AuthState.getAuthMessage(): String {
    return when (this) {
        AuthState.AUTHENTICATED -> ""
        AuthState.NOT_AUTHENTICATED -> stringResource(R.string.auth_failed)
        AuthState.AUTH_IN_PROGRESS -> stringResource(R.string.authenticating)
        AuthState.AUTH_ERROR -> stringResource(R.string.auth_error)
    }
}

@Preview(name = "AuthScreenProgress Light")
@Preview(name = "AuthScreenProgress Night", uiMode = Configuration.UI_MODE_NIGHT_YES)@Composable
private fun AuthScreenProgressPreview() {
    MoneyFlowTheme {
        Surface {
            AuthScreen(
                authState = AuthState.AUTH_IN_PROGRESS,
                onRetryClick = {},
            )
        }
    }
}

@Preview(name = "uthScreenFail Light")
@Preview(name = "uthScreenFail Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AuthScreenFailPreview() {
    MoneyFlowTheme {
        Surface {
            AuthScreen(
                authState = AuthState.NOT_AUTHENTICATED,
                onRetryClick = {},
            )
        }
    }
}

@Preview(name = "AuthScreenError Light")
@Preview(name = "AuthScreenError Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AuthScreenErrorPreview() {
    MoneyFlowTheme {
        Surface {
            AuthScreen(
                authState = AuthState.AUTH_ERROR,
                onRetryClick = {},
            )
        }
    }
}

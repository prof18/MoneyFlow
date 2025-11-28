package com.prof18.moneyflow.presentation.auth

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
import androidx.compose.ui.tooling.preview.Preview
import com.prof18.moneyflow.ui.style.Margins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import money_flow.shared.generated.resources.Res
import money_flow.shared.generated.resources.auth_error
import money_flow.shared.generated.resources.auth_failed
import money_flow.shared.generated.resources.authenticating
import money_flow.shared.generated.resources.retry
import org.jetbrains.compose.resources.stringResource

@Composable
fun AuthScreen(
    authState: AuthState,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
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
                    text = stringResource(Res.string.retry),
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
        AuthState.NOT_AUTHENTICATED -> stringResource(Res.string.auth_failed)
        AuthState.AUTH_IN_PROGRESS -> stringResource(Res.string.authenticating)
        AuthState.AUTH_ERROR -> stringResource(Res.string.auth_error)
    }
}

@Preview(name = "AuthScreenProgress Light")
@Composable
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

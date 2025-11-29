package com.prof18.moneyflow.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.prof18.moneyflow.features.settings.BiometricAvailabilityChecker
import com.prof18.moneyflow.ui.components.SwitchWithText
import com.prof18.moneyflow.ui.style.Margins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import money_flow.shared.generated.resources.Res
import money_flow.shared.generated.resources.biometric_support
import money_flow.shared.generated.resources.hide_sensitive_data
import money_flow.shared.generated.resources.security
import money_flow.shared.generated.resources.settings_screen
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SettingsScreen(
    biometricAvailabilityChecker: BiometricAvailabilityChecker,
    biometricState: Boolean,
    onBiometricEnabled: (Boolean) -> Unit,
    hideSensitiveDataState: Boolean,
    onHideSensitiveDataEnabled: (Boolean) -> Unit,
    paddingValues: PaddingValues,
) {
    SettingsScreenContent(
        isBiometricSupported = biometricAvailabilityChecker.isBiometricSupported(),
        biometricState = biometricState,
        onBiometricEnabled = onBiometricEnabled,
        hideSensitiveDataState = hideSensitiveDataState,
        onHideSensitiveDataEnabled = onHideSensitiveDataEnabled,
        paddingValues = paddingValues,
    )
}

@Composable
@Suppress("LongMethod") // TODO: reduce method length
private fun SettingsScreenContent(
    isBiometricSupported: Boolean,
    biometricState: Boolean,
    onBiometricEnabled: (Boolean) -> Unit,
    hideSensitiveDataState: Boolean,
    onHideSensitiveDataEnabled: (Boolean) -> Unit,
    paddingValues: PaddingValues,
) {
    Scaffold(
        modifier = Modifier.padding(paddingValues),
        topBar = {
            Text(
                text = stringResource(Res.string.settings_screen),
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = Margins.regular)
                    .padding(top = Margins.regular),
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(innerPadding)
                    .padding(top = Margins.regular),
            ) {
                Text(
                    text = stringResource(Res.string.security),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = Margins.regular),
                )

                SwitchWithText(
                    onSwitchChanged = onHideSensitiveDataEnabled,
                    switchStatus = hideSensitiveDataState,
                    title = stringResource(Res.string.hide_sensitive_data),
                )

                if (isBiometricSupported) {
                    SwitchWithText(
                        onSwitchChanged = onBiometricEnabled,
                        switchStatus = biometricState,
                        title = stringResource(Res.string.biometric_support),
                    )
                }
            }
        },
    )
}

@Preview(name = "Settings Light")
@Composable
private fun SettingsScreenPreview() {
    MoneyFlowTheme {
        Surface {
            SettingsScreenContent(
                biometricState = true,
                isBiometricSupported = true,
                onBiometricEnabled = {},
                hideSensitiveDataState = true,
                onHideSensitiveDataEnabled = {},
                paddingValues = PaddingValues(),
            )
        }
    }
}

package com.prof18.moneyflow

import androidx.compose.ui.window.ComposeUIViewController
import com.prof18.moneyflow.presentation.MoneyFlowApp

@Suppress("FunctionName")
public fun MainViewController() = ComposeUIViewController {
    MoneyFlowApp(
        biometricAuthenticator = IosBiometricAuthenticator(),
    )
}

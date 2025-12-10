package com.prof18.moneyflow.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.prof18.moneyflow.MainViewModel
import com.prof18.moneyflow.features.authentication.BiometricAuthenticator
import com.prof18.moneyflow.navigation.MoneyFlowNavHost
import com.prof18.moneyflow.presentation.auth.AuthScreen
import com.prof18.moneyflow.presentation.auth.AuthState
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MoneyFlowApp(
    biometricAuthenticator: BiometricAuthenticator,
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<MainViewModel>()
    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.performAuthentication(biometricAuthenticator)
    }

    MoneyFlowTheme {
        Box(modifier = modifier.fillMaxSize()) {
            MoneyFlowNavHost()

            AnimatedVisibility(visible = authState != AuthState.AUTHENTICATED) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.safeDrawing),
                ) {
                    AuthScreen(
                        authState = authState,
                        onRetryClick = { viewModel.performAuthentication(biometricAuthenticator) },
                    )
                }
            }
        }
    }
}

package com.prof18.moneyflow

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.prof18.moneyflow.features.auth.AuthScreen
import com.prof18.moneyflow.features.auth.AuthState
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : FragmentActivity() {

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private val viewModel: MainViewModel by viewModel()

    private var authState: AuthState by mutableStateOf(AuthState.AUTH_IN_PROGRESS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupAuthentication()
        if (!viewModel.isBiometricEnabled()) {
            authState = AuthState.AUTHENTICATED
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE,
        )

        setContent {
            MoneyFlowTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    AppContainer()

                    AnimatedVisibility(visible = authState != AuthState.AUTHENTICATED) {
                        Surface(modifier = Modifier.fillMaxSize()) {
                            AuthScreen(authState = authState, onRetryClick = { performAuth() })
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        performAuth()
    }

    override fun onStop() {
        super.onStop()
        if (viewModel.isBiometricEnabled() && isBiometricSupported()) {
            authState = AuthState.NOT_AUTHENTICATED
        }
    }

    private fun performAuth() {
        if (viewModel.isBiometricEnabled() && isBiometricSupported()) {
            authState = AuthState.AUTH_IN_PROGRESS
            biometricPrompt.authenticate(promptInfo)
        }
    }

    private fun setupAuthentication() {
        val executor = ContextCompat.getMainExecutor(this@MainActivity)
        biometricPrompt = BiometricPrompt(
            this@MainActivity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence,
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    authState = AuthState.AUTH_ERROR
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult,
                ) {
                    super.onAuthenticationSucceeded(result)
                    authState = AuthState.AUTHENTICATED
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    authState = AuthState.NOT_AUTHENTICATED
                }
            },
        )

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            // Can't call setNegativeButtonText() and
            // setAllowedAuthenticators(... or DEVICE_CREDENTIAL) at the same time.
            // .setNegativeButtonText("Use account password")
            // if (allowDeviceCredential) setAllowedAuthenticators(BIOMETRIC_STRONG or BIOMETRIC_WEAK or )
            // else setNegativeButtonText("Cancel")
            .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
            .build()
    }

    private fun isBiometricSupported(): Boolean {
        val biometricManager = BiometricManager.from(this)
        return when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> {
                Timber.d("Reached some auth state. It should be impossible to reach this state!")
                false
            }
        }
    }
}

package com.prof18.moneyflow

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.prof18.moneyflow.features.authentication.BiometricAuthenticator

class AndroidBiometricAuthenticator(
    private val activity: FragmentActivity,
) : BiometricAuthenticator {

    private var onSuccess: (() -> Unit)? = null
    private var onFailure: (() -> Unit)? = null
    private var onError: (() -> Unit)? = null

    private val biometricPrompt: BiometricPrompt by lazy {
        val executor = ContextCompat.getMainExecutor(activity)
        BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence,
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    onError?.invoke()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess?.invoke()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onFailure?.invoke()
                }
            },
        )
    }

    private val promptInfo: BiometricPrompt.PromptInfo by lazy {
        BiometricPrompt.PromptInfo.Builder()
            .setTitle("MoneyFlow")
            .setSubtitle("Unlock MoneyFlow")
            .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
            .build()
    }

    override fun canAuthenticate(): Boolean {
        val biometricManager = BiometricManager.from(activity)
        return biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL) ==
            BiometricManager.BIOMETRIC_SUCCESS
    }

    override fun authenticate(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        onError: () -> Unit,
    ) {
        this.onSuccess = onSuccess
        this.onFailure = onFailure
        this.onError = onError

        biometricPrompt.authenticate(promptInfo)
    }
}

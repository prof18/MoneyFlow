package com.prof18.moneyflow

import android.content.Context
import androidx.biometric.BiometricManager
import co.touchlab.kermit.Logger
import com.prof18.moneyflow.features.settings.BiometricAvailabilityChecker

class AndroidBiometricAvailabilityChecker(
    private val context: Context,
) : BiometricAvailabilityChecker {

    override fun isBiometricSupported(): Boolean {
        val biometricManager = BiometricManager.from(context)
        return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> {
                Logger.d { "Biometric not supported or not available on this device." }
                false
            }
        }
    }
}

package com.prof18.moneyflow

import com.prof18.moneyflow.features.settings.BiometricAvailabilityChecker
import kotlinx.cinterop.ExperimentalForeignApi
import platform.LocalAuthentication.LAContext
import platform.LocalAuthentication.LAPolicyDeviceOwnerAuthenticationWithBiometrics

@OptIn(ExperimentalForeignApi::class)
internal class IosBiometricAvailabilityChecker : BiometricAvailabilityChecker {
    override fun isBiometricSupported(): Boolean {
        val context = LAContext()
        return context.canEvaluatePolicy(LAPolicyDeviceOwnerAuthenticationWithBiometrics, null)
    }
}

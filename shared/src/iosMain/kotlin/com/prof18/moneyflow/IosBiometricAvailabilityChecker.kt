package com.prof18.moneyflow

import com.prof18.moneyflow.features.settings.BiometricAvailabilityChecker
import kotlinx.cinterop.ExperimentalForeignApi
import platform.LocalAuthentication.LAContext
import platform.LocalAuthentication.LAPolicyDeviceOwnerAuthentication

@OptIn(ExperimentalForeignApi::class)
class IosBiometricAvailabilityChecker : BiometricAvailabilityChecker {
    override fun isBiometricSupported(): Boolean {
        val context = LAContext()
        return context.canEvaluatePolicy(LAPolicyDeviceOwnerAuthentication, null)
    }
}

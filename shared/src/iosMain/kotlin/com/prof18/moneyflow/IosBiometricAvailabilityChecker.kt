package com.prof18.moneyflow

import com.prof18.moneyflow.features.settings.BiometricAvailabilityChecker

class IosBiometricAvailabilityChecker : BiometricAvailabilityChecker {
    override fun isBiometricSupported(): Boolean = false
}
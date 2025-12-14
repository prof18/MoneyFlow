package com.prof18.moneyflow.features.settings

internal interface BiometricAvailabilityChecker {
    fun isBiometricSupported(): Boolean
}

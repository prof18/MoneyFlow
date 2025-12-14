package com.prof18.moneyflow

import com.prof18.moneyflow.features.authentication.BiometricAuthenticator
import kotlinx.cinterop.ExperimentalForeignApi
import platform.LocalAuthentication.LAContext
import platform.LocalAuthentication.LAPolicyDeviceOwnerAuthentication
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

@OptIn(ExperimentalForeignApi::class)
internal class IosBiometricAuthenticator : BiometricAuthenticator {

    override fun canAuthenticate(): Boolean {
        val context = LAContext()
        return context.canEvaluatePolicy(LAPolicyDeviceOwnerAuthentication, null)
    }

    override fun authenticate(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        onError: () -> Unit,
    ) {
        val context = LAContext()
        context.evaluatePolicy(
            policy = LAPolicyDeviceOwnerAuthentication,
            localizedReason = "Unlock MoneyFlow",
            reply = { success, error ->
                dispatch_async(dispatch_get_main_queue()) {
                    when {
                        success -> onSuccess()
                        error != null -> onError()
                        else -> onFailure()
                    }
                }
            },
        )
    }
}

package com.prof18.moneyflow.features.authentication

interface BiometricAuthenticator {
    fun canAuthenticate(): Boolean

    fun authenticate(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        onError: () -> Unit,
    )
}

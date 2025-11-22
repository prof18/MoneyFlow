package com.prof18.moneyflow.features.auth

internal enum class AuthState {
    AUTHENTICATED,
    NOT_AUTHENTICATED,
    AUTH_IN_PROGRESS,
    AUTH_ERROR,
}

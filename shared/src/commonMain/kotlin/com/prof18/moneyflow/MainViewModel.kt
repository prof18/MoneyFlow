package com.prof18.moneyflow

import androidx.lifecycle.ViewModel
import com.prof18.moneyflow.data.SettingsRepository
import com.prof18.moneyflow.features.authentication.BiometricAuthenticator
import com.prof18.moneyflow.features.settings.BiometricAvailabilityChecker
import com.prof18.moneyflow.presentation.auth.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(
    private val settingsRepository: SettingsRepository,
    private val biometricAvailabilityChecker: BiometricAvailabilityChecker,
) : ViewModel() {

    private val _authState = MutableStateFlow(initialState())
    val authState: StateFlow<AuthState> = _authState

    fun performAuthentication(biometricAuthenticator: BiometricAuthenticator) {
        if (!shouldUseBiometrics(biometricAuthenticator)) {
            _authState.value = AuthState.AUTHENTICATED
            return
        }

        _authState.value = AuthState.AUTH_IN_PROGRESS
        biometricAuthenticator.authenticate(
            onSuccess = { _authState.value = AuthState.AUTHENTICATED },
            onFailure = { _authState.value = AuthState.NOT_AUTHENTICATED },
            onError = { _authState.value = AuthState.AUTH_ERROR },
        )
    }

    fun lockIfNeeded(biometricAuthenticator: BiometricAuthenticator) {
        if (shouldUseBiometrics(biometricAuthenticator)) {
            _authState.value = AuthState.NOT_AUTHENTICATED
        }
    }

    private fun initialState(): AuthState {
        return if (settingsRepository.isBiometricEnabled()) {
            AuthState.NOT_AUTHENTICATED
        } else {
            AuthState.AUTHENTICATED
        }
    }

    private fun shouldUseBiometrics(biometricAuthenticator: BiometricAuthenticator): Boolean {
        return settingsRepository.isBiometricEnabled() && biometricAuthenticator.canAuthenticate() &&
            biometricAvailabilityChecker.isBiometricSupported()
    }
}

package com.prof18.moneyflow.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {
    fun isBiometricEnabled(): Boolean

    fun setBiometric(enabled: Boolean)

    val hideSensibleDataState: StateFlow<Boolean>

    fun setHideSensitiveData(hide: Boolean)
}

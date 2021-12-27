package com.prof18.moneyflow.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {

    fun getDropboxClientCred(): String?

    fun getLastDropboxRefreshTime(): String?

    fun saveDropboxClientCred(clientCred: String)

    fun saveLastDropboxRefreshTime(lastRefresh: Long)

    fun isBiometricEnabled(): Boolean

    fun setBiometric(enabled: Boolean)

    val hideSensibleDataState: StateFlow<Boolean>

    fun setHideSensitiveData(hide: Boolean)
}
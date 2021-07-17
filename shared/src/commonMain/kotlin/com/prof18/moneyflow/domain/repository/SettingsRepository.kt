package com.prof18.moneyflow.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {

    @Throws(Exception::class)
    fun getDropboxClientCred(): String?

    @Throws(Exception::class)
    fun getLastDropboxRefreshTime(): String?

    @Throws(Exception::class)
    fun saveDropboxClientCred(clientCred: String)

    @Throws(Exception::class)
    fun saveLastDropboxRefreshTime(lastRefresh: Long)

    @Throws(Exception::class)
    fun isBiometricEnabled(): Boolean

    @Throws(Exception::class)
    fun setBiometric(enabled: Boolean)

    val hideSensibleDataState: StateFlow<Boolean>

    @Throws(Exception::class)
    fun setHideSensitiveData(hide: Boolean)
}
package com.prof18.moneyflow.domain.repository

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

}
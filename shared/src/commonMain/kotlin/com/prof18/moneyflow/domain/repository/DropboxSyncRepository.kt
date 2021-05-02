package com.prof18.moneyflow.domain.repository

interface DropboxSyncRepository {

    @Throws(Exception::class)
    fun getClientCred(): String?

    @Throws(Exception::class)
    fun getLastRefreshTime(): String?

    @Throws(Exception::class)
    fun saveClientCred(clientCred: String)

    @Throws(Exception::class)
    fun saveLastRefresh(lastRefresh: Long)

}
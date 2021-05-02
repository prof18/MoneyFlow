package com.prof18.moneyflow.presentation.dropboxsync

interface DropboxSyncUserCase {
    fun saveClientCred(string: String)
    fun getClientCred(): String?

    fun saveLastRefresh(lastRefreshMillis: Long)
    fun getLastRefresh(): String?
}
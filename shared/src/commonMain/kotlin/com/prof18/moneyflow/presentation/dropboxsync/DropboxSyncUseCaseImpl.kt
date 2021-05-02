package com.prof18.moneyflow.presentation.dropboxsync

import com.prof18.moneyflow.domain.repository.DropboxSyncRepository

class DropboxSyncUseCaseImpl(
    private val dropboxSyncRepository: DropboxSyncRepository,

) : DropboxSyncUserCase {

    override fun saveClientCred(string: String) {
        dropboxSyncRepository.saveClientCred(string)
    }

    override fun getClientCred(): String? {
        return dropboxSyncRepository.getClientCred()
    }

    override fun saveLastRefresh(lastRefreshMillis: Long) {
        dropboxSyncRepository.saveLastRefresh(lastRefreshMillis)
    }

    override fun getLastRefresh(): String? {
        return dropboxSyncRepository.getLastRefreshTime()
    }
}
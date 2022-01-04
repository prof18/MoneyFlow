package com.prof18.moneyflow.presentation.dropboxsync

import com.prof18.moneyflow.domain.entities.DropboxClientStatus
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.domain.repository.DropboxSyncRepository
import com.prof18.moneyflow.dropboxapi.DropboxAuthorizationParam
import kotlinx.coroutines.flow.StateFlow

class DropboxSyncUseCase(
    private val dropboxSyncRepository: DropboxSyncRepository
) {

    val dropboxClientStatus: StateFlow<DropboxClientStatus> = dropboxSyncRepository.dropboxConnectionStatus

    // TODO: transform as string here?
    val lastDropboxSync: StateFlow<Long?> = dropboxSyncRepository.lastDropboxSync

    fun startAuthFlow(authorizationParam: DropboxAuthorizationParam) =
        dropboxSyncRepository.startDropboxAuthorization(authorizationParam)

    fun saveDropboxAuth(): MoneyFlowResult<Unit> = dropboxSyncRepository.saveDropboxAuthorization()

}
package com.prof18.moneyflow.domain.repository

import com.prof18.moneyflow.domain.entities.DatabaseDownloadData
import com.prof18.moneyflow.domain.entities.DatabaseUploadData
import com.prof18.moneyflow.domain.entities.DropboxClientStatus
import com.prof18.moneyflow.domain.entities.DropboxSyncMetadata
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.dropboxapi.DropboxAuthorizationParam
import com.prof18.moneyflow.dropboxapi.DropboxSetupParam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface DropboxSyncRepository {

    val dropboxConnectionStatus: StateFlow<DropboxClientStatus>
    fun setupDropboxApp(setupParam: DropboxSetupParam)
    fun startDropboxAuthorization(authorizationParam: DropboxAuthorizationParam)
    suspend fun saveDropboxAuthorization(): MoneyFlowResult<Unit>
    suspend fun restoreDropboxClient(): MoneyFlowResult<Unit>
    suspend fun unlinkDropboxClient()
    suspend fun upload(databaseUploadData: DatabaseUploadData): MoneyFlowResult<Unit>
    fun getDropboxSyncMetadata(): Flow<DropboxSyncMetadata>
    suspend fun download(databaseDownloadData: DatabaseDownloadData): MoneyFlowResult<Unit>
}
package com.prof18.moneyflow.domain.repository

import com.prof18.moneyflow.domain.entities.DatabaseDownloadData
import com.prof18.moneyflow.domain.entities.DatabaseUploadData
import com.prof18.moneyflow.domain.entities.DropboxClientStatus
import com.prof18.moneyflow.domain.entities.DropboxSyncMetadata
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.dropboxapi.DropboxAuthorizationParam
import com.prof18.moneyflow.dropboxapi.DropboxDownloadResult
import com.prof18.moneyflow.dropboxapi.DropboxHandleOAuthRequestParam
import com.prof18.moneyflow.dropboxapi.DropboxSetupParam
import kotlinx.coroutines.flow.StateFlow

interface DropboxSyncRepository {

    val dropboxConnectionStatus: StateFlow<DropboxClientStatus>
    val dropboxMetadataFlow: StateFlow<DropboxSyncMetadata>
    fun setupDropboxApp(setupParam: DropboxSetupParam)
    fun startDropboxAuthorization(authorizationParam: DropboxAuthorizationParam)
    fun handleOAuthResponse(oAuthRequestParam: DropboxHandleOAuthRequestParam)
    suspend fun saveDropboxAuthorization(): MoneyFlowResult<Unit>
    suspend fun restoreDropboxClient(): MoneyFlowResult<Unit>
    suspend fun unlinkDropboxClient()
    suspend fun upload(databaseUploadData: DatabaseUploadData): MoneyFlowResult<Unit>
    suspend fun download(databaseDownloadData: DatabaseDownloadData): MoneyFlowResult<DropboxDownloadResult>
}

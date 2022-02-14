package com.prof18.moneyflow.domain.repository

import com.prof18.moneyflow.domain.entities.DatabaseData
import com.prof18.moneyflow.domain.entities.DropboxClientStatus
import com.prof18.moneyflow.domain.entities.DropboxSyncTimestamp
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
    suspend fun upload(databaseData: DatabaseData): MoneyFlowResult<Unit>
    fun getDropboxSyncTimestamps(): Flow<DropboxSyncTimestamp>
}
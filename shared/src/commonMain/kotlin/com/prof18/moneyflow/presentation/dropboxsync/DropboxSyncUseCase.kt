package com.prof18.moneyflow.presentation.dropboxsync

import com.prof18.moneyflow.domain.entities.DatabaseData
import com.prof18.moneyflow.domain.entities.DropboxClientStatus
import com.prof18.moneyflow.domain.entities.DropboxSyncTimestamp
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.domain.repository.DropboxSyncRepository
import com.prof18.moneyflow.dropboxapi.DropboxAuthorizationParam
import com.prof18.moneyflow.platform.LocalizedStringProvider
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.utils.formatFullDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class DropboxSyncUseCase(
    private val dropboxSyncRepository: DropboxSyncRepository,
    private val errorMapper: MoneyFlowErrorMapper,
    private val localizedStringProvider: LocalizedStringProvider,
) {

    val dropboxClientStatus: StateFlow<DropboxClientStatus> = dropboxSyncRepository.dropboxConnectionStatus

    fun startAuthFlow(authorizationParam: DropboxAuthorizationParam) =
        dropboxSyncRepository.startDropboxAuthorization(authorizationParam)

    suspend fun saveDropboxAuth(): MoneyFlowResult<Unit> = dropboxSyncRepository.saveDropboxAuthorization()

    suspend fun unlinkDropbox() = dropboxSyncRepository.unlinkDropboxClient()

    suspend fun restoreDropboxClient(): MoneyFlowResult<Unit> = dropboxSyncRepository.restoreDropboxClient()

    suspend fun upload(databaseData: DatabaseData): MoneyFlowResult<Unit> =
        dropboxSyncRepository.upload(databaseData)

    fun observeDropboxSyncModel(): Flow<DropboxSyncTimestampModel> =
        dropboxSyncRepository.getDropboxSyncTimestamps()
            .map {
                val uploadDate = getUploadDate(it)
                val downloadDate = getDownloadDate(it)
                DropboxSyncTimestampModel.Success(
                    latestUploadFormattedDate = uploadDate,
                    latestDownloadFormattedDate = downloadDate,
                )
            }

    private fun getDownloadDate(
        syncTimestamp: DropboxSyncTimestamp,
    ): String = if (syncTimestamp.lastDownloadTimestamp != null) {
        localizedStringProvider.get("dropbox_latest_download_date",
            syncTimestamp.lastDownloadTimestamp.formatFullDate())
    } else {
        localizedStringProvider.get("dropbox_no_download_date")
    }

    private fun getUploadDate(
        syncTimestamp: DropboxSyncTimestamp,
    ): String = if (syncTimestamp.lastUploadTimestamp != null) {
        localizedStringProvider.get("dropbox_latest_upload_date", syncTimestamp.lastUploadTimestamp.formatFullDate())
    } else {
        localizedStringProvider.get("dropbox_no_upload_date")
    }
}
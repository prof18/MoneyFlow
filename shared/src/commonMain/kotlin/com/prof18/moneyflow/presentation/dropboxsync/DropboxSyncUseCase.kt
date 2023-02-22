package com.prof18.moneyflow.presentation.dropboxsync

import com.prof18.moneyflow.data.DropboxSyncRepository
import com.prof18.moneyflow.domain.entities.DropboxClientStatus
import com.prof18.moneyflow.domain.entities.DropboxSyncMetadata
import com.prof18.moneyflow.domain.entities.MoneyFlowResult
import com.prof18.moneyflow.dropbox.DropboxDownloadParam
import com.prof18.moneyflow.dropbox.DropboxDownloadResult
import com.prof18.moneyflow.dropbox.DropboxUploadParam
import com.prof18.moneyflow.platform.LocalizedStringProvider
import com.prof18.moneyflow.utils.formatFullDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlin.native.HiddenFromObjC
import kotlin.native.ObjCName

@ObjCName("_DropboxSyncUseCase")
@Suppress("TooManyFunctions") // TODO: maybe fix and create a mapper?
class DropboxSyncUseCase(
    private val dropboxSyncRepository: DropboxSyncRepository,
    private val localizedStringProvider: LocalizedStringProvider,
) {

    @HiddenFromObjC
    val dropboxClientStatus: StateFlow<DropboxClientStatus> = dropboxSyncRepository.dropboxConnectionStatus

    @HiddenFromObjC
    fun startAuthFlow(platformAuthHandler: () -> Unit) =
        dropboxSyncRepository.startDropboxAuthorization(platformAuthHandler)

    @HiddenFromObjC
    fun setupClient(apiKey: String) = dropboxSyncRepository.setupDropboxApp(apiKey)

    @HiddenFromObjC
    fun handleOAuthResponse(platformOAuthResponseHandler: () -> Unit) {
        dropboxSyncRepository.handleOAuthResponse(platformOAuthResponseHandler)
    }

    @HiddenFromObjC
    suspend fun saveDropboxAuth(): MoneyFlowResult<Unit> = dropboxSyncRepository.saveDropboxAuthorization()

    @HiddenFromObjC
    suspend fun unlinkDropbox() = dropboxSyncRepository.unlinkDropboxClient()

    @HiddenFromObjC
    suspend fun restoreDropboxClient(): MoneyFlowResult<Unit> = dropboxSyncRepository.restoreDropboxClient()

    @HiddenFromObjC
    suspend fun upload(dropboxUploadParam: DropboxUploadParam): MoneyFlowResult<Unit> =
        dropboxSyncRepository.upload(dropboxUploadParam)

    @HiddenFromObjC
    suspend fun download(downloadParam: DropboxDownloadParam): MoneyFlowResult<DropboxDownloadResult> =
        dropboxSyncRepository.download(downloadParam)

    @HiddenFromObjC
    fun observeDropboxSyncMetadataModel(): Flow<DropboxSyncMetadataModel> =
        dropboxSyncRepository.dropboxMetadataFlow
            .map {
                DropboxSyncMetadataModel.Success(
                    latestUploadFormattedDate = getUploadDate(it),
                    latestDownloadFormattedDate = getDownloadDate(it),
                    latestUploadHash = getUploadHash(it),
                    latestDownloadHash = getDownloadHash(it),
                    tlDrHashMessage = getTlDrHashMessage(it),
                )
            }

    private fun getTlDrHashMessage(syncMetadata: DropboxSyncMetadata): String? = when {
        syncMetadata.lastUploadHash == null || syncMetadata.lastDownloadHash == null -> null
        syncMetadata.lastUploadHash == syncMetadata.lastDownloadHash -> {
            localizedStringProvider.get("tl_dr_dropbox_same_hash_message")
        }
        else -> localizedStringProvider.get("tl_dr_dropbox_different_hash_message")
    }

    private fun getDownloadDate(
        syncMetadata: DropboxSyncMetadata,
    ): String = if (syncMetadata.lastDownloadTimestamp != null) {
        localizedStringProvider.get(
            "dropbox_latest_download_date",
            syncMetadata.lastDownloadTimestamp.formatFullDate(),
        )
    } else {
        localizedStringProvider.get("dropbox_no_download_date")
    }

    private fun getUploadDate(
        syncMetadata: DropboxSyncMetadata,
    ): String = if (syncMetadata.lastUploadTimestamp != null) {
        localizedStringProvider.get("dropbox_latest_upload_date", syncMetadata.lastUploadTimestamp.formatFullDate())
    } else {
        localizedStringProvider.get("dropbox_no_upload_date")
    }

    private fun getDownloadHash(
        syncMetadata: DropboxSyncMetadata,
    ): String = if (syncMetadata.lastDownloadHash != null) {
        localizedStringProvider.get("dropbox_latest_download_content_hash", syncMetadata.lastDownloadHash)
    } else {
        localizedStringProvider.get("dropbox_no_download_date")
    }

    private fun getUploadHash(
        syncMetadata: DropboxSyncMetadata,
    ): String = if (syncMetadata.lastUploadHash != null) {
        localizedStringProvider.get("dropbox_latest_upload_content_hash", syncMetadata.lastUploadHash)
    } else {
        localizedStringProvider.get("dropbox_no_upload_date")
    }
}

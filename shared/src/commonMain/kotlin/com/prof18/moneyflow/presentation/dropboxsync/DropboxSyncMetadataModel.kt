package com.prof18.moneyflow.presentation.dropboxsync

import com.prof18.moneyflow.presentation.model.UIErrorMessage

sealed class DropboxSyncMetadataModel {
    object Loading : DropboxSyncMetadataModel()
    data class Success(
        val latestUploadFormattedDate: String,
        val latestDownloadFormattedDate: String,
        val latestUploadHash: String,
        val latestDownloadHash: String,
        val tlDrHashMessage: String?,
    ) : DropboxSyncMetadataModel()

    class Error(
        val errorMessage: UIErrorMessage,
    ) : DropboxSyncMetadataModel()
}

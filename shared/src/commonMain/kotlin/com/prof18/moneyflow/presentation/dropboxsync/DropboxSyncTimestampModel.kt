package com.prof18.moneyflow.presentation.dropboxsync

import com.prof18.moneyflow.presentation.model.UIErrorMessage

sealed class DropboxSyncTimestampModel {
    object Loading : DropboxSyncTimestampModel()
    data class Success(
        val latestUploadFormattedDate: String,
        val latestDownloadFormattedDate: String,
    ) : DropboxSyncTimestampModel()

    class Error(
        val errorMessage: UIErrorMessage,
    ) : DropboxSyncTimestampModel()
}

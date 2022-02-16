package com.prof18.moneyflow.domain.entities

import com.prof18.moneyflow.utils.MillisSinceEpoch

data class DropboxSyncMetadata(
    val lastUploadTimestamp: MillisSinceEpoch?,
    val lastDownloadTimestamp: MillisSinceEpoch?,
    val lastUploadHash: String?,
    val lastDownloadHash: String?,
) {
    companion object {
        fun empty(): DropboxSyncMetadata = DropboxSyncMetadata(
            lastUploadTimestamp = null,
            lastDownloadTimestamp = null,
            lastUploadHash = null,
            lastDownloadHash = null,
        )
    }
}

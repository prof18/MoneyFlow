package com.prof18.moneyflow.domain.entities

import com.prof18.moneyflow.utils.MillisSinceEpoch

data class DropboxSyncTimestamp(
    val lastUploadTimestamp: MillisSinceEpoch?,
    val lastDownloadTimestamp: MillisSinceEpoch?,
)

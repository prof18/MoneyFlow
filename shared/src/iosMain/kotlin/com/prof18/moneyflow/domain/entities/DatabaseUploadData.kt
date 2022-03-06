package com.prof18.moneyflow.domain.entities

import platform.Foundation.NSData

actual data class DatabaseUploadData(
    val path: String,
    val data: NSData,
)

actual data class DatabaseDownloadData(
    val outputName: String,
    val path: String,
)

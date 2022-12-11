package com.prof18.moneyflow.dropbox

data class DropboxUploadResult(
    val id: String,
    val editDateMillis: Long,
    val sizeInByte: Long,
    val contentHash: String?,
)

data class DropboxDownloadResult(
    val id: String,
    val sizeInByte: Long,
    val contentHash: String?,
    val destinationUrl: DatabaseDestinationUrl? = null,
)

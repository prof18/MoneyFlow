package com.prof18.moneyflow.dropboxapi

data class DropboxUploadResult(
    val id: String,
    val editDateMillis: Long,
    val sizeInByte: Long,
    val contentHash: String?,
)

expect class DropboxDownloadResult

expect class DropboxCredentials
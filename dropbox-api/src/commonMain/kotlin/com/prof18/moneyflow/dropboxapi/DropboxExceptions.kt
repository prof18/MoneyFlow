package com.prof18.moneyflow.dropboxapi

data class DropboxUploadException(
    val errorMessage: String? = null,
    val exceptionCause: Throwable? = null,
) : Exception(errorMessage, exceptionCause)

data class DropboxDownloadException(
    val errorMessage: String? = null,
    val exceptionCause: Throwable? = null,
) : Exception(errorMessage, exceptionCause)

data class DropboxException(
    val errorMessage: String
): Exception(errorMessage)
package com.prof18.moneyflow.dropboxapi

data class DropboxUploadException(
    val errorMessage: String
) : Exception(message = errorMessage)

data class DropboxDownloadException(
    val errorMessage: String
) : Exception(message = errorMessage)

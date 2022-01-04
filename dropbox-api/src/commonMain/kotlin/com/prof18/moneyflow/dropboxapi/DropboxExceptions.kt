package com.prof18.moneyflow.dropboxapi

data class DropboxUploadException(
    val errorMessage: String
) : Exception(errorMessage)

data class DropboxDownloadException(
    val errorMessage: String
) : Exception(errorMessage)

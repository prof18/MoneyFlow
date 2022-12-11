package com.prof18.moneyflow.dropbox

interface DropboxDataSource {

    fun setup(apiKey: String)

    fun startAuthorization(platformAuthHandler: () -> Unit)

    fun handleOAuthResponse(platformOAuthResponseHandler: () -> Unit)

    fun restoreAuth(stringCredentials: DropboxStringCredentials)

    fun saveAuth(): DropboxStringCredentials

    /**
     * Can throw a [DropboxException]
     */
    fun revokeAccess()

    fun isClientSet(): Boolean

    /**
     * If successful returns a [DropboxUploadResult] otherwise throws a [DropboxUploadException]
     */
    suspend fun performUpload(uploadParam: DropboxUploadParam): DropboxUploadResult
    /**
     * If successful returns a [DropboxDownloadResult] otherwise throws a [DropboxUploadException]
     */
    suspend fun performDownload(downloadParam: DropboxDownloadParam): DropboxDownloadResult
}

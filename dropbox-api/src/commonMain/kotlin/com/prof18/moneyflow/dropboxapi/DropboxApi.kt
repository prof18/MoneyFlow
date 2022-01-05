package com.prof18.moneyflow.dropboxapi

expect class DropboxApi() {
    fun setup(setupParam: DropboxSetupParam)
    fun startAuthorization(authParam: DropboxAuthorizationParam)
    fun handleOAuthResponse(oAuthRequestParam: DropboxHandleOAuthRequestParam)
    fun getClient(clientIdentifier: String, credentials: DropboxCredentials): DropboxClient?
    /**
     * Can throw a [DropboxException]
     */
    fun revokeAccess(client: DropboxClient)
    fun getCredentials(): DropboxCredentials?

    /**
     * Can throw JsonParseException, JsonReadException or IOException
     */
    fun getCredentialsFromString(stringCredentials: String): DropboxCredentials?
    /**
     * If successful returns a [DropboxUploadResult] otherwise throws a [DropboxUploadException]
     */
    suspend fun performUpload(uploadParam: DropboxUploadParam): DropboxUploadResult
    /**
     * If successful returns a [DropboxDownloadResult] otherwise throws a [DropboxUploadException]
     */
    suspend fun performDownload(downloadParam: DropboxDownloadParam): DropboxDownloadResult
}
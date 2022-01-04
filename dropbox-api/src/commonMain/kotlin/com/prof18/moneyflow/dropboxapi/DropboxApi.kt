package com.prof18.moneyflow.dropboxapi

expect class DropboxApi {
    fun setup(setupParam: DropboxSetupParam)
    fun startAuthorization(authParam: DropboxAuthorizationParam)
    fun handleOAuthResponse(oAuthRequestParam: DropboxHandleOAuthRequestParam)
    fun getClient(clientParam: DropboxClientParam): DropboxClient?
    fun revokeAccess()
    /**
     * If successful returns a [DropboxUploadResult] otherwise throws a [DropboxUploadException]
     */
    suspend fun performUpload(uploadParam: DropboxUploadParam): DropboxUploadResult
    /**
     * If successful returns a [DropboxDownloadResult] otherwise throws a [DropboxUploadException]
     */
    suspend fun performDownload(downloadParam: DropboxDownloadParam): DropboxDownloadResult
    // TODO: maybe add a method to get the credentials?
}
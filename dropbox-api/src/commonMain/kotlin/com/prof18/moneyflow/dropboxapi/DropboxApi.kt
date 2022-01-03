package com.prof18.moneyflow.dropboxapi

expect class DropboxApi {
    fun setup()
    fun startAuthorization(authData: DropboxAuthorizationData)
    fun handleOAuthResponse(oAuthRequestData: DropboxHandleOAuthRequestData)
    fun getClient(clientData: DropboxClientData): DropboxClient?
    fun revokeAccess()
    // TODO: make it suspend?
    fun performUpload(uploadData: DropboxUploadData)
    // TODO: make it suspend?
    fun performDownload(downloadData: DropboxDownloadData)
    // TODO: maybe add a method to get the credentials?
}
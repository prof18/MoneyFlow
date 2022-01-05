package com.prof18.moneyflow.data.dropbox

import com.prof18.moneyflow.dropboxapi.*

interface DropboxSource {

    fun setup(setupParam: DropboxSetupParam)
    fun startAuthorization(authParam: DropboxAuthorizationParam)
    fun handleOAuthResponse(oAuthRequestParam: DropboxHandleOAuthRequestParam)
    fun getClient(clientIdentifier: String, credentials: DropboxCredentials): DropboxClient?
    fun revokeAccess(client: DropboxClient)
    fun getCredentials(): DropboxCredentials?
    fun getCredentialsFromString(stringCredentials: String): DropboxCredentials?
    suspend fun performUpload(uploadParam: DropboxUploadParam): DropboxUploadResult
    suspend fun performDownload(downloadParam: DropboxDownloadParam): DropboxDownloadResult
}
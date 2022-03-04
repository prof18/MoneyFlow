package com.prof18.moneyflow.data.dropbox

import com.prof18.moneyflow.dropboxapi.DropboxAuthorizationParam
import com.prof18.moneyflow.dropboxapi.DropboxClient
import com.prof18.moneyflow.dropboxapi.DropboxCredentials
import com.prof18.moneyflow.dropboxapi.DropboxDownloadParam
import com.prof18.moneyflow.dropboxapi.DropboxDownloadResult
import com.prof18.moneyflow.dropboxapi.DropboxHandleOAuthRequestParam
import com.prof18.moneyflow.dropboxapi.DropboxSetupParam
import com.prof18.moneyflow.dropboxapi.DropboxUploadParam
import com.prof18.moneyflow.dropboxapi.DropboxUploadResult

internal interface DropboxSource {

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

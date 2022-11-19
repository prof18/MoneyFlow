package com.prof18.moneyflow.data.dropbox

import com.prof18.moneyflow.dropbox.DropboxAuthorizationParam
import com.prof18.moneyflow.dropbox.DropboxClient
import com.prof18.moneyflow.dropbox.DropboxCredentials
import com.prof18.moneyflow.dropbox.DropboxDownloadParam
import com.prof18.moneyflow.dropbox.DropboxDownloadResult
import com.prof18.moneyflow.dropbox.DropboxHandleOAuthRequestParam
import com.prof18.moneyflow.dropbox.DropboxSetupParam
import com.prof18.moneyflow.dropbox.DropboxUploadParam
import com.prof18.moneyflow.dropbox.DropboxUploadResult

internal interface DropboxSource {

    fun setup(setupParam: DropboxSetupParam)
    fun startAuthorization(authParam: DropboxAuthorizationParam)
    fun handleOAuthResponse(oAuthRequestParam: DropboxHandleOAuthRequestParam)
    fun getClient(clientIdentifier: String, credentials: DropboxCredentials): DropboxClient?
    fun revokeAccess(client: DropboxClient)
    fun getCredentials(): DropboxCredentials?
    fun getCredentialsFromString(stringCredentials: String): DropboxCredentials
    suspend fun performUpload(uploadParam: DropboxUploadParam): DropboxUploadResult
    suspend fun performDownload(downloadParam: DropboxDownloadParam): DropboxDownloadResult
}

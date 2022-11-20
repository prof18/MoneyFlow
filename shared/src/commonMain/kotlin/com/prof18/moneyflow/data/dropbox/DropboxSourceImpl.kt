package com.prof18.moneyflow.data.dropbox

import com.prof18.moneyflow.dropbox.DropboxApi
import com.prof18.moneyflow.dropbox.DropboxAuthorizationParam
import com.prof18.moneyflow.dropbox.DropboxClient
import com.prof18.moneyflow.dropbox.DropboxCredentials
import com.prof18.moneyflow.dropbox.DropboxDownloadParam
import com.prof18.moneyflow.dropbox.DropboxDownloadResult
import com.prof18.moneyflow.dropbox.DropboxHandleOAuthRequestParam
import com.prof18.moneyflow.dropbox.DropboxSetupParam
import com.prof18.moneyflow.dropbox.DropboxUploadParam
import com.prof18.moneyflow.dropbox.DropboxUploadResult

internal class DropboxSourceImpl(
    private val dropboxApi: DropboxApi,
) : DropboxSource {

    override fun setup(setupParam: DropboxSetupParam) = dropboxApi.setup(setupParam)

    override fun startAuthorization(authParam: DropboxAuthorizationParam) = dropboxApi.startAuthorization(authParam)

    override fun handleOAuthResponse(oAuthRequestParam: DropboxHandleOAuthRequestParam) =
        dropboxApi.handleOAuthResponse(oAuthRequestParam)

    override fun getClient(clientIdentifier: String, credentials: DropboxCredentials): DropboxClient? =
        dropboxApi.getClient(clientIdentifier, credentials)

    override fun revokeAccess(client: DropboxClient) = dropboxApi.revokeAccess(client)

    override fun getCredentials(): DropboxCredentials? = dropboxApi.getCredentials()

    override fun getCredentialsFromString(stringCredentials: String): DropboxCredentials =
        dropboxApi.getCredentialsFromString(stringCredentials)

    override suspend fun performUpload(uploadParam: DropboxUploadParam): DropboxUploadResult =
        dropboxApi.performUpload(uploadParam)

    override suspend fun performDownload(downloadParam: DropboxDownloadParam): DropboxDownloadResult =
        dropboxApi.performDownload(downloadParam)
}

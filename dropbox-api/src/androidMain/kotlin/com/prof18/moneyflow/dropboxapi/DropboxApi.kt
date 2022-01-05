package com.prof18.moneyflow.dropboxapi

import co.touchlab.kermit.Logger
import com.dropbox.core.DbxApiException
import com.dropbox.core.DbxException
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.android.Auth
import com.dropbox.core.oauth.DbxCredential
import com.dropbox.core.v2.DbxClientV2
import java.util.*

actual class DropboxApi {

    actual fun setup(setupParam: DropboxSetupParam) {
        // No op, nothing required on Android side
    }

    actual fun startAuthorization(authParam: DropboxAuthorizationParam) {
        Auth.startOAuth2Authentication(authParam.activity, authParam.apiKey)
    }

    actual fun handleOAuthResponse(oAuthRequestParam: DropboxHandleOAuthRequestParam) {
        // TODO? is required?
    }

    actual fun getClient(clientIdentifier: String, credentials: DropboxCredentials): DropboxClient? {
        val userLocale: String = Locale.getDefault().toString()
        val requestConfig = DbxRequestConfig
            .newBuilder(clientIdentifier)
            .withUserLocale(userLocale)
            .build()
        return DbxClientV2(requestConfig, credentials)
    }

    actual fun revokeAccess(client: DropboxClient) {
        try {
            client.auth().tokenRevoke()
        } catch (e: DbxException) {
            val message = "Error during revoking dropbox access"
            Logger.e { message }
            throw DropboxException(e.message ?: message)
        }
    }

    actual fun getCredentials(): DropboxCredentials? {
        return Auth.getDbxCredential()
    }

    actual fun getCredentialsFromString(stringCredentials: String): DropboxCredentials? {
        return DbxCredential.Reader.readFully(stringCredentials)
    }

    actual suspend fun performUpload(uploadParam: DropboxUploadParam): DropboxUploadResult {
        TODO()
    }

    actual suspend fun performDownload(downloadParam: DropboxDownloadParam): DropboxDownloadResult {
        TODO()
    }

}


package com.prof18.moneyflow.dropboxapi

import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.android.Auth
import com.dropbox.core.v2.DbxClientV2
import java.util.*

actual class DropboxApi {
    actual fun setup() {
        // No op, nothing required on Android side
    }

    actual fun startAuthorization(authData: DropboxAuthorizationData) {
        Auth.startOAuth2Authentication(authData.activity, authData.apiKey)
    }

    actual fun handleOAuthResponse(oAuthRequestData: DropboxHandleOAuthRequestData) {
        // TODO? is required?
    }

    actual fun getClient(clientData: DropboxClientData): DropboxClient? {
        val userLocale: String = Locale.getDefault().toString()
        val requestConfig = DbxRequestConfig
            .newBuilder(clientData.clientIdentifier)
            .withUserLocale(userLocale)
            .build()
        return DbxClientV2(requestConfig, clientData.credential)
    }

    actual fun revokeAccess() {
        // TODO: implement revoke time
    }

    actual fun performUpload(uploadData: DropboxUploadData) {
        // TODO: implement upload
    }

    actual fun performDownload(downloadData: DropboxDownloadData) {
        // TODO: implement download
    }

}


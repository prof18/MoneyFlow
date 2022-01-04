package com.prof18.moneyflow.dropboxapi

import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.android.Auth
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

    actual fun getClient(clientParam: DropboxClientParam): DropboxClient? {
        val userLocale: String = Locale.getDefault().toString()
        val requestConfig = DbxRequestConfig
            .newBuilder(clientParam.clientIdentifier)
            .withUserLocale(userLocale)
            .build()
        return DbxClientV2(requestConfig, clientParam.credential)
    }

    actual fun revokeAccess() {
        // TODO: implement revoke time
    }

    actual suspend fun performUpload(uploadParam: DropboxUploadParam): DropboxUploadResult {
        TODO()
    }

    actual suspend fun performDownload(downloadParam: DropboxDownloadParam): DropboxDownloadResult {
        TODO()
    }

}


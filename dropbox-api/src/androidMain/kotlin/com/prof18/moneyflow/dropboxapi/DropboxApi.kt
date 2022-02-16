package com.prof18.moneyflow.dropboxapi

import co.touchlab.kermit.Logger
import com.dropbox.core.DbxException
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.android.Auth
import com.dropbox.core.oauth.DbxCredential
import com.dropbox.core.v2.DbxClientV2
import com.dropbox.core.v2.files.WriteMode
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.FileInputStream
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

actual class DropboxApi {

    actual fun setup(setupParam: DropboxSetupParam) {
        // No op, nothing required on Android side
    }

    actual fun startAuthorization(authParam: DropboxAuthorizationParam) {
        val requestConfig = DbxRequestConfig(authParam.clientIdentifier)
        Auth.startOAuth2PKCE(authParam.activity, authParam.apiKey, requestConfig, authParam.scopes)
    }

    actual fun handleOAuthResponse(oAuthRequestParam: DropboxHandleOAuthRequestParam) {
        // no-op on Android
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
            client.refreshAccessToken()
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

    actual suspend fun performUpload(uploadParam: DropboxUploadParam): DropboxUploadResult =
        suspendCancellableCoroutine { continuation ->
            try {
                val metadata = uploadParam.client.files()
                    ?.uploadBuilder(uploadParam.path)
                    ?.withMode(WriteMode.OVERWRITE)
                    ?.uploadAndFinish(FileInputStream(uploadParam.file))

                val id = metadata?.id
                val editTime = metadata?.serverModified
                val size = metadata?.size ?: 0
                val hash = metadata?.contentHash
                Logger.d { "Dropbox content hash on upload is: $hash" }

                if (id != null && editTime != null) {
                    val uploadResult = DropboxUploadResult(
                        id = id,
                        editDateMillis = editTime.time,
                        sizeInByte = size,
                        contentHash = hash
                    )
                    continuation.resume(uploadResult)
                } else {
                    Logger.e { "Metadata from Dropbox are null" }
                    continuation.resumeWithException(DropboxUploadException("Metadata from Dropbox are null"))
                }
            } catch (e: Exception) {
                Logger.e(e) { "Error while uploading data on Dropbox" }
                continuation.resumeWithException(DropboxUploadException(exceptionCause = e))
            }
        }

    actual suspend fun performDownload(downloadParam: DropboxDownloadParam): DropboxDownloadResult =
        suspendCancellableCoroutine { continuation ->
            try {
                val metadata = downloadParam.client.files()
                    .downloadBuilder(downloadParam.path)
                    .download(downloadParam.outputStream)

                val id = metadata?.id
                val contentHash = metadata?.contentHash
                val sizeInBytes = metadata?.size ?: 0
                Logger.d { "Dropbox content hash on download is: $contentHash" }
                if (id != null) {
                    val downloadResult = DropboxDownloadResult(
                        id = id,
                        sizeInByte = sizeInBytes,
                        contentHash = contentHash,
                    )
                    continuation.resume(downloadResult)
                } else {
                    Logger.e { "Metadata from Dropbox are null" }
                    continuation.resumeWithException(DropboxDownloadException("Metadata from Dropbox are null"))
                }

            } catch (e: Exception) {
                Logger.e(e) { "Error while downloading data from Dropbox" }
                continuation.resumeWithException(DropboxDownloadException(exceptionCause = e))
            }
        }
}

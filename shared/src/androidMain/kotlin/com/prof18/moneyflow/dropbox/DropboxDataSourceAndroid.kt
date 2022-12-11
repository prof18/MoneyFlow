package com.prof18.moneyflow.dropbox

import android.app.Activity
import co.touchlab.kermit.Logger
import com.dropbox.core.DbxException
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.android.Auth
import com.dropbox.core.oauth.DbxCredential
import com.dropbox.core.v2.DbxClientV2
import com.dropbox.core.v2.files.WriteMode
import com.prof18.moneyflow.utils.DropboxConstants
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.FileInputStream
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class DropboxDataSourceAndroid : DropboxDataSource {

    private var dropboxClient: DbxClientV2? = null

    override fun setup(apiKey: String) {
        // No op, nothing required on Android side
    }

    override fun startAuthorization(platformAuthHandler: () -> Unit) = platformAuthHandler()

    override fun handleOAuthResponse(platformOAuthResponseHandler: () -> Unit) {
        // no-op on Android
    }

    // todo: create a method to get the status of the client

    override fun saveAuth(): DropboxStringCredentials {
        val credentials = getStoredCredentials()
        credentials?.let { dropboxClient = createClient(credentials) }
        return DropboxStringCredentials(
            value = credentials.toString(),
        )
    }

    override fun restoreAuth(stringCredentials: DropboxStringCredentials) {
        if (dropboxClient != null) {
            // Avoid setting up again
            return
        }
        val credentials = getCredentialsFromString(stringCredentials.value)
        if (credentials != null) {
            dropboxClient = createClient(credentials)
        }
    }

    private fun createClient(credentials: DbxCredential): DbxClientV2 {
        val userLocale: String = Locale.getDefault().toString()
        val requestConfig = DbxRequestConfig
            .newBuilder(DropboxConstants.DROPBOX_CLIENT_IDENTIFIER)
            .withUserLocale(userLocale)
            .build()
        return DbxClientV2(requestConfig, credentials)
    }

    override fun revokeAccess() {
        val client = requireNotNull(dropboxClient)
        try {
            client.refreshAccessToken()
            client.auth().tokenRevoke()
            dropboxClient = null
        } catch (e: DbxException) {
            val message = "Error during revoking dropbox access"
            Logger.e { message }
            throw DropboxException(e, e.message ?: message)
        }
    }

    override fun isClientSet(): Boolean =
        dropboxClient != null

    private fun getStoredCredentials(): DbxCredential? {
        return Auth.getDbxCredential()
    }

    private fun getCredentialsFromString(stringCredentials: String): DbxCredential? {
        return try {
            DbxCredential.Reader.readFully(stringCredentials)
        } catch (e: Exception) {
            Logger.e("Unable to create credentials from string", e)
            null
        }
    }

    override suspend fun performUpload(uploadParam: DropboxUploadParam): DropboxUploadResult =
        suspendCancellableCoroutine { continuation ->
            try {
                val client = requireNotNull(dropboxClient)
                val metadata = client.files()
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
                        contentHash = hash,
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

    override suspend fun performDownload(downloadParam: DropboxDownloadParam): DropboxDownloadResult =
        suspendCancellableCoroutine { continuation ->
            try {
                val client = requireNotNull(dropboxClient)
                val metadata = client.files()
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

    companion object {
        fun startAuth(activity: Activity, apiKey: String) {
            val requestConfig = DbxRequestConfig(DropboxConstants.DROPBOX_CLIENT_IDENTIFIER)
            Auth.startOAuth2PKCE(activity, apiKey, requestConfig, DropboxConstants.DROPBOX_SCOPES)
        }
    }
}

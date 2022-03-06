package com.prof18.moneyflow.dropboxapi

import co.touchlab.kermit.Logger
import cocoapods.ObjectiveDropboxOfficial.DBClientsManager
import cocoapods.ObjectiveDropboxOfficial.DBFILESDownloadError
import cocoapods.ObjectiveDropboxOfficial.DBFILESFileMetadata
import cocoapods.ObjectiveDropboxOfficial.DBFILESUploadError
import cocoapods.ObjectiveDropboxOfficial.DBFILESWriteMode
import cocoapods.ObjectiveDropboxOfficial.DBOAuthCompletion
import cocoapods.ObjectiveDropboxOfficial.DBScopeRequest
import cocoapods.ObjectiveDropboxOfficial.DBScopeTypeUser
import cocoapods.ObjectiveDropboxOfficial.authorizeFromControllerV2
import cocoapods.ObjectiveDropboxOfficial.setupWithAppKey
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.URLByAppendingPathComponent
import platform.Foundation.timeIntervalSince1970
import platform.UIKit.UIApplication
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

actual class DropboxApi {

    actual fun setup(setupParam: DropboxSetupParam) {
        DBClientsManager.setupWithAppKey(setupParam.apiKey)
    }

    actual fun startAuthorization(authParam: DropboxAuthorizationParam) {
        val scopeRequest = DBScopeRequest(
            scopeType = DBScopeTypeUser,
            scopes = authParam.scopes,
            includeGrantedScopes = false,
        )
        DBClientsManager.authorizeFromControllerV2(
            sharedApplication = UIApplication.sharedApplication,
            controller = authParam.viewController,
            loadingStatusDelegate = null,
            openURL = { url ->
                url?.let { UIApplication.sharedApplication.openURL(it) }
            },
            scopeRequest = scopeRequest
        )
    }

    actual fun handleOAuthResponse(oAuthRequestParam: DropboxHandleOAuthRequestParam) {
        val completion: DBOAuthCompletion = { authResult ->
            when {
                authResult?.isSuccess() == true -> {
                    Logger.d { "Success! User is logged into DropboxClientsManager." }
                    oAuthRequestParam.onSuccess()
                }
                authResult?.isCancel() == true -> {
                    Logger.d { "Authorization flow was manually canceled by user!" }
                    oAuthRequestParam.onCancel()
                }
                authResult?.isError() == true -> {
                    Logger.e { "Error during dropbox auth: ${authResult.errorDescription}" }
                    oAuthRequestParam.onError()
                }
                authResult == null -> {
                    Logger.e { "DropboxA Auth Result is null" }
                    oAuthRequestParam.onError()
                }
            }
        }
        DBClientsManager.handleRedirectURL(
            url = oAuthRequestParam.url,
            completion = completion,
        )
    }

    actual fun getClient(clientIdentifier: String, credentials: DropboxCredentials): DropboxClient? {
        return DBClientsManager.authorizedClient()
    }

    actual fun revokeAccess(client: DropboxClient) {
        DBClientsManager.unlinkAndResetClients()
    }

    actual fun getCredentials(): DropboxCredentials {
        // No-op on iOS
        return IOS_DROPBOX_CREDENTIALS
    }

    actual fun getCredentialsFromString(stringCredentials: String): DropboxCredentials {
        // No-op on iOS
        return IOS_DROPBOX_CREDENTIALS
    }

    actual suspend fun performUpload(uploadParam: DropboxUploadParam): DropboxUploadResult =
        suspendCancellableCoroutine { continuation ->
            uploadParam.client.filesRoutes.uploadData(
                path = uploadParam.path,
                mode = DBFILESWriteMode(overwrite = Unit),
                autorename = null,
                clientModified = null,
                inputData = uploadParam.data,
                mute = null,
                propertyGroups = null,
                strictConflict = null,
            ).setResponseBlock { result, routeError, networkError ->
                if (result is DBFILESFileMetadata? && result != null) {
                    Logger.d { "Data successfully uploaded to Dropbox" }

                    @Suppress("MagicNumber")
                    val uploadResult = DropboxUploadResult(
                        id = result.id_,
                        editDateMillis = result.serverModified.timeIntervalSince1970().toLong() * 1000,
                        sizeInByte = result.size.longValue,
                        contentHash = result.contentHash
                    )
                    continuation.resume(uploadResult)
                } else {
                    if (networkError != null) {
                        Logger.e { "Network error during dropbox upload: ${networkError.errorContent}" }
                        continuation.resumeWithException(DropboxUploadException(networkError.errorContent ?: ""))
                    } else if (routeError is DBFILESUploadError? && routeError != null) {
                        Logger.e { "Route error during dropbox upload: ${routeError.description}" }
                        continuation.resumeWithException(DropboxUploadException(routeError.description ?: ""))
                    }
                }
            }
        }

    actual suspend fun performDownload(downloadParam: DropboxDownloadParam): DropboxDownloadResult =
        suspendCoroutine { continuation ->
            val fileManager = NSFileManager.defaultManager
            val outputDir = fileManager.URLsForDirectory(directory = NSDocumentDirectory, inDomains = NSUserDomainMask)
                .firstOrNull() as? NSURL?
            val outputUrl = outputDir?.URLByAppendingPathComponent(downloadParam.outputName)
            if (outputUrl == null) {
                val message = "The output url was null during downloading from Dropbox"
                Logger.e { message }
                continuation.resumeWithException(DropboxDownloadException(errorMessage = message))
            }
            downloadParam.client.filesRoutes.downloadUrl(
                path = downloadParam.path,
                overwrite = true,
                destination = outputUrl!!
            ).setResponseBlock { result, routeError, dbRequestError, destinationUrl ->
                if (result is DBFILESFileMetadata? && result != null && destinationUrl != null) {
                    Logger.d { "Data successfully downloaded from Dropbox" }
                    val downloadResult = DropboxDownloadResult(
                        id = result.id_,
                        sizeInByte = result.size.longValue,
                        contentHash = result.contentHash,
                        destinationUrl = destinationUrl
                    )
                    continuation.resume(downloadResult)
                } else {
                    if (dbRequestError != null) {
                        Logger.e { "Network error during Dropbox download: ${dbRequestError.errorContent}" }
                        continuation.resumeWithException(
                            DropboxDownloadException(
                                errorMessage = dbRequestError.errorContent ?: ""
                            )
                        )
                    } else if (routeError is DBFILESDownloadError? && routeError != null) {
                        Logger.e { "Route error during Dropbox download: ${routeError.description}" }
                        continuation.resumeWithException(
                            DropboxDownloadException(
                                errorMessage = routeError.description ?: ""
                            )
                        )
                    }
                }
            }
        }

    private companion object {
        const val IOS_DROPBOX_CREDENTIALS = "IOS_DROPBOX_CREDENTIALS"
    }
}

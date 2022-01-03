package com.prof18.moneyflow.dropbox

import co.touchlab.kermit.Logger
import cocoapods.ObjectiveDropboxOfficial.*
import platform.Foundation.*
import platform.UIKit.UIApplication

actual class DropboxApi {

    actual fun setup() {
        var apiKey = ""
        val path = NSBundle.mainBundle.pathForResource("Keys", "plist")
        if (path != null) {
            val dict = NSDictionary.create(contentsOfURL = NSURL(fileURLWithPath = path))
            apiKey = dict?.run {
                this.objectForKey("DropboxApiKey") as? String
            } ?: ""
        }
        DBClientsManager.setupWithAppKey(apiKey)
    }

    actual fun startAuthorization(authData: DropboxAuthorizationData) {
        val scopeRequest = DBScopeRequest(
            scopeType = DBScopeTypeUser,
            scopes = listOf("files.content.write", "files.content.read", "files.metadata.read"),
            includeGrantedScopes = false,
        )
        DBClientsManager.authorizeFromControllerV2(
            sharedApplication = UIApplication.sharedApplication,
            controller = authData.viewController,
            loadingStatusDelegate = null,
            openURL = { url ->
                url?.let { UIApplication.sharedApplication.openURL(it) }
            },
            scopeRequest = scopeRequest
        )
    }

    actual fun handleOAuthResponse(oAuthRequestData: DropboxHandleOAuthRequestData) {
        val completion: DBOAuthCompletion = { authResult ->
            when {
                authResult?.isSuccess() == true -> {
                    Logger.d { "Success! User is logged into DropboxClientsManager." }
                    oAuthRequestData.onSuccess()
                }
                authResult?.isCancel() == true -> {
                    Logger.d { "Authorization flow was manually canceled by user!" }
                    oAuthRequestData.onCancel()
                }
                authResult?.isError() == true -> {
                    Logger.e { "Error during dropbox auth: ${authResult.errorDescription}" }
                    oAuthRequestData.onError()
                }
                authResult == null -> {
                    Logger.e { "DropboxA Auth Result is null" }
                    oAuthRequestData.onError()
                }
            }
        }
        DBClientsManager.handleRedirectURL(
            url = oAuthRequestData.url,
            completion = completion,
        )
    }

    actual fun getClient(clientData: DropboxClientData): DropboxClient? {
        return DBClientsManager.authorizedClient()
    }

    actual fun revokeAccess() {
        DBClientsManager.unlinkAndResetClients()
    }

    actual fun performUpload(uploadData: DropboxUploadData) {
        uploadData.client.filesRoutes.uploadData(
            path = uploadData.path,
            inputData = uploadData.data,
        ).setResponseBlock { result, routeError, networkError ->
            if (result is DBFILESFileMetadata? && result != null) {
                Logger.d { "Data successfully uploaded to Dropbox" }
                uploadData.onSuccess(result)
            } else {
                if (networkError != null) {
                    Logger.e { "Network error during dropbox upload: ${networkError.errorContent}" }
                } else if (routeError is DBFILESUploadError? && routeError != null) {
                    Logger.e { "Route error during dropbox upload: ${routeError.description}" }
                }
                uploadData.onError()
            }
        }.setProgressBlock { bytesUploaded, totalBytesUploaded, totalBytesExpectedToUpload ->
            uploadData.onProgress(bytesUploaded, totalBytesUploaded, totalBytesExpectedToUpload)
        }
    }

    actual fun performDownload(downloadData: DropboxDownloadData) {
        val fileManager = NSFileManager.defaultManager
        val outputDir = fileManager.URLsForDirectory(directory = NSDocumentDirectory, inDomains = NSUserDomainMask)
            .firstOrNull() as? NSURL?
        val outputUrl = outputDir?.URLByAppendingPathComponent(downloadData.outputName)
        if (outputUrl == null) {
            Logger.e { "The output url was null during downloading from Dropbox" }
            return
        }
        downloadData.client.filesRoutes.downloadUrl(
            path = downloadData.path,
            overwrite = true,
            destination = outputUrl
        ).setResponseBlock { result, routeError, dbRequestError, destinationUrl ->
            if (result is DBFILESFileMetadata? && result != null && destinationUrl != null) {
                Logger.d { "Data successfully downloaded from Dropbox" }
                downloadData.onSuccess(result, destinationUrl)
            } else {
                if (dbRequestError != null) {
                    Logger.e { "Network error during Dropbox download: ${dbRequestError.errorContent}" }
                } else if (routeError is DBFILESDownloadError? && routeError != null) {
                    Logger.e { "Route error during Dropbox download: ${routeError.description}" }
                }
                downloadData.onError()
            }
        }.setProgressBlock { bytesDownloaded, totalBytesDownloaded, totalBytesExpectedToDownload ->
            downloadData.onProgress(bytesDownloaded, totalBytesDownloaded, totalBytesExpectedToDownload)
        }
    }
}







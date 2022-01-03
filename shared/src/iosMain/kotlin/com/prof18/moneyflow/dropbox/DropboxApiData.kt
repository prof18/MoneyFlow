package com.prof18.moneyflow.dropbox

import cocoapods.ObjectiveDropboxOfficial.DBFILESFileMetadata
import cocoapods.ObjectiveDropboxOfficial.DBUserClient
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.UIKit.UIViewController

actual typealias DropboxClient = DBUserClient

actual class DropboxAuthorizationData(
    val viewController: UIViewController,
)

actual class DropboxHandleOAuthRequestData(
    val url: NSURL,
    val onSuccess: () -> Unit,
    val onCancel: () -> Unit,
    val onError: () -> Unit,
)

actual class DropboxClientData

actual class DropboxUploadData(
    val client: DropboxClient,
    val path: String,
    val data: NSData,
    val onSuccess: (DBFILESFileMetadata) -> Unit,
    val onError: () -> Unit,
    // bytesUploaded, totalBytesUploaded, totalBytesExpectedToUpload
    val onProgress: (Long, Long, Long) -> Unit
)

actual class DropboxDownloadData(
    val client: DropboxClient,
    val outputName: String,
    val path: String,
    val onSuccess: (DBFILESFileMetadata, NSURL) -> Unit,
    val onError: () -> Unit,
    // bytesDownloaded, totalBytesDownloaded, totalBytesExpectedToDownload
    val onProgress: (Long, Long, Long) -> Unit
)

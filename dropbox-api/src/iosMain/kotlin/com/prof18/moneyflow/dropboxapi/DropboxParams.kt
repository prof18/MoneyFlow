package com.prof18.moneyflow.dropboxapi

import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.UIKit.UIViewController

actual class DropboxSetupParam(
    val apiKey: String
)

actual class DropboxAuthorizationParam(
    val viewController: UIViewController,
    val scopes: List<String>,
)

actual class DropboxHandleOAuthRequestParam(
    val url: NSURL,
    val onSuccess: () -> Unit,
    val onCancel: () -> Unit,
    val onError: () -> Unit,
)

actual class DropboxUploadParam(
    val client: DropboxClient,
    val path: String,
    val data: NSData,
)

actual class DropboxDownloadParam(
    val client: DropboxClient,
    val outputName: String,
    val path: String,
)

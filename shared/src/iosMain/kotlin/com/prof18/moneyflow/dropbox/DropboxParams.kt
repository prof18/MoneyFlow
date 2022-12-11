package com.prof18.moneyflow.dropbox

import platform.Foundation.NSData

actual class DropboxUploadParam(
    val path: String,
    val data: NSData,
)

actual class DropboxDownloadParam(
    val outputName: String,
    val path: String,
)

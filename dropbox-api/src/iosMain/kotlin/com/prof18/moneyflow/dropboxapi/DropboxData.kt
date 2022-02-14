package com.prof18.moneyflow.dropboxapi

import cocoapods.ObjectiveDropboxOfficial.DBFILESFileMetadata
import platform.Foundation.NSURL

actual class DropboxDownloadResult(
    val metadata: DBFILESFileMetadata,
    val destinationUrl: NSURL
)

actual class DropboxCredentials
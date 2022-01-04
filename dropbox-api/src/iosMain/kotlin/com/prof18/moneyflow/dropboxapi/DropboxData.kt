package com.prof18.moneyflow.dropboxapi

import cocoapods.ObjectiveDropboxOfficial.DBFILESFileMetadata
import platform.Foundation.NSURL

actual typealias DropboxUploadResult = DBFILESFileMetadata

actual class DropboxDownloadResult(
    val metadata: DBFILESFileMetadata,
    val destinationUrl: NSURL
)
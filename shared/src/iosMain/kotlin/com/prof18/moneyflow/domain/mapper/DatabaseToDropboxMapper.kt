package com.prof18.moneyflow.domain.mapper

import com.prof18.moneyflow.domain.entities.DatabaseDownloadData
import com.prof18.moneyflow.domain.entities.DatabaseUploadData
import com.prof18.moneyflow.dropboxapi.DropboxClient
import com.prof18.moneyflow.dropboxapi.DropboxDownloadParam
import com.prof18.moneyflow.dropboxapi.DropboxUploadParam
import platform.Foundation.NSData

internal actual fun DatabaseUploadData.toDropboxUploadParams(dropboxClient: DropboxClient): DropboxUploadParam {
    // TODO: add real data!
    return DropboxUploadParam(
        client = dropboxClient,
        path = "",
        data = NSData()
    )
}

internal actual fun DatabaseDownloadData.toDropboxDownloadParams(dropboxClient: DropboxClient): DropboxDownloadParam {
    // TODO: add real data
    return DropboxDownloadParam(
        client = dropboxClient,
        outputName = "",
        path = ""
    )
}
package com.prof18.moneyflow.domain.mapper

import com.prof18.moneyflow.domain.entities.DatabaseDownloadData
import com.prof18.moneyflow.domain.entities.DatabaseUploadData
import com.prof18.moneyflow.dropbox.DropboxClient
import com.prof18.moneyflow.dropbox.DropboxDownloadParam
import com.prof18.moneyflow.dropbox.DropboxUploadParam

internal actual fun DatabaseUploadData.toDropboxUploadParams(dropboxClient: DropboxClient): DropboxUploadParam {
    return DropboxUploadParam(
        client = dropboxClient,
        path = this.path,
        data = this.data,
    )
}

internal actual fun DatabaseDownloadData.toDropboxDownloadParams(dropboxClient: DropboxClient): DropboxDownloadParam {
    return DropboxDownloadParam(
        client = dropboxClient,
        outputName = this.outputName,
        path = this.path,
    )
}

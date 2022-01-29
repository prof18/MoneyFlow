package com.prof18.moneyflow.domain.mapper

import com.prof18.moneyflow.domain.entities.DatabaseData
import com.prof18.moneyflow.dropboxapi.DropboxClient
import com.prof18.moneyflow.dropboxapi.DropboxUploadParam
import platform.Foundation.NSData

actual fun DatabaseData.toDropboxUploadParams(dropboxClient: DropboxClient): DropboxUploadParam {
    // TODO: add real data!
    return DropboxUploadParam(
        client = dropboxClient,
        path = "",
        data = NSData()
    )
}
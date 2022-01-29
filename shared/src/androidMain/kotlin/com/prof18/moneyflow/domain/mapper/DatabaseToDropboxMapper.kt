package com.prof18.moneyflow.domain.mapper

import com.prof18.moneyflow.domain.entities.DatabaseData
import com.prof18.moneyflow.dropboxapi.DropboxClient
import com.prof18.moneyflow.dropboxapi.DropboxUploadParam

actual fun DatabaseData.toDropboxUploadParams(dropboxClient: DropboxClient): DropboxUploadParam {
    return DropboxUploadParam(
        client = dropboxClient,
        path = path,
        file = file
    )
}
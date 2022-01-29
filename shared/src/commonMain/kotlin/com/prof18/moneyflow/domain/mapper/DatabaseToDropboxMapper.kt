package com.prof18.moneyflow.domain.mapper

import com.prof18.moneyflow.domain.entities.DatabaseData
import com.prof18.moneyflow.dropboxapi.DropboxClient
import com.prof18.moneyflow.dropboxapi.DropboxUploadParam

expect fun DatabaseData.toDropboxUploadParams(dropboxClient: DropboxClient): DropboxUploadParam
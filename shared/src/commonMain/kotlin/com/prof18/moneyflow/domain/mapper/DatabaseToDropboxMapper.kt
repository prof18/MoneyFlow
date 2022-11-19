package com.prof18.moneyflow.domain.mapper

import com.prof18.moneyflow.domain.entities.DatabaseDownloadData
import com.prof18.moneyflow.domain.entities.DatabaseUploadData
import com.prof18.moneyflow.dropbox.DropboxClient
import com.prof18.moneyflow.dropbox.DropboxDownloadParam
import com.prof18.moneyflow.dropbox.DropboxUploadParam

internal expect fun DatabaseUploadData.toDropboxUploadParams(dropboxClient: DropboxClient): DropboxUploadParam

internal expect fun DatabaseDownloadData.toDropboxDownloadParams(dropboxClient: DropboxClient): DropboxDownloadParam

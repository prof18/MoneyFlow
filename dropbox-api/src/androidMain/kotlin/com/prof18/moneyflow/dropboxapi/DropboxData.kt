package com.prof18.moneyflow.dropboxapi

import com.dropbox.core.oauth.DbxCredential
import com.dropbox.core.v2.files.FileMetadata

actual typealias DropboxUploadResult = FileMetadata

// TODO: move to a typealias when knowing the return type
actual class DropboxDownloadResult

actual typealias DropboxCredentials = DbxCredential
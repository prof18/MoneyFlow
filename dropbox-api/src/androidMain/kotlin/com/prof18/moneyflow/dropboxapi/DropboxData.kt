package com.prof18.moneyflow.dropboxapi

import com.dropbox.core.oauth.DbxCredential
import com.dropbox.core.v2.files.FileMetadata

actual typealias DropboxCredentials = DbxCredential

actual typealias DatabaseDestinationUrl = Nothing // On Android is not necessary
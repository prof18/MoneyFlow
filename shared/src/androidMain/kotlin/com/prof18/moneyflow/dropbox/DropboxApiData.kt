package com.prof18.moneyflow.dropbox

import android.app.Activity
import com.dropbox.core.oauth.DbxCredential

actual typealias DropboxClient =  com.dropbox.core.v2.DbxClientV2

actual class DropboxAuthorizationData(
    val activity: Activity,
    val apiKey: String
)

actual class DropboxClientData(
    val credential: DbxCredential,
    val clientIdentifier: String
)

actual class DropboxHandleOAuthRequestData

// TODO: add data!
actual class DropboxUploadData

// TODO: add data!!
actual class DropboxDownloadData
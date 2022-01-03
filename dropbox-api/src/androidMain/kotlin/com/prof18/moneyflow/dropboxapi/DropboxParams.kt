package com.prof18.moneyflow.dropboxapi

import android.app.Activity
import com.dropbox.core.oauth.DbxCredential

actual class DropboxAuthorizationData(
    val activity: Activity,
    val apiKey: String
)

actual class DropboxHandleOAuthRequestData

actual class DropboxUploadData

actual class DropboxDownloadData

actual class DropboxClientData(
    val credential: DbxCredential,
    val clientIdentifier: String
)
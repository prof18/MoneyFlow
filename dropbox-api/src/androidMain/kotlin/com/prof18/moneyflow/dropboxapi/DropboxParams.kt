package com.prof18.moneyflow.dropboxapi

import android.app.Activity
import com.dropbox.core.oauth.DbxCredential

actual class DropboxSetupParam

actual class DropboxAuthorizationParam(
    val activity: Activity,
    val apiKey: String
)

actual class DropboxHandleOAuthRequestParam

actual class DropboxUploadParam

actual class DropboxDownloadParam

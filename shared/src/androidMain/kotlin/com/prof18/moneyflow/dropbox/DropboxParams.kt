package com.prof18.moneyflow.dropbox

import android.app.Activity
import java.io.File
import java.io.OutputStream

actual class DropboxSetupParam

actual class DropboxAuthorizationParam(
    val activity: Activity,
    val apiKey: String,
    val clientIdentifier: String,
    val scopes: List<String>,
)

actual class DropboxHandleOAuthRequestParam

actual class DropboxUploadParam(
    val client: DropboxClient,
    val path: String,
    val file: File,
)

actual class DropboxDownloadParam(
    val client: DropboxClient,
    val path: String,
    val outputStream: OutputStream,
)

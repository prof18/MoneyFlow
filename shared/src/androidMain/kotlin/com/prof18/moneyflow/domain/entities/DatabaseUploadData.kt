package com.prof18.moneyflow.domain.entities

import java.io.File
import java.io.OutputStream

actual class DatabaseUploadData(
    val path: String,
    val file: File,
)

actual class DatabaseDownloadData(
    val path: String,
    val outputStream: OutputStream,
)

package com.prof18.moneyflow.domain.entities

import java.io.File
import java.io.OutputStream

actual data class DatabaseUploadData(
    val path: String,
    val file: File,
)

actual data class DatabaseDownloadData(
    val path: String,
    val outputStream: OutputStream,
)

package com.prof18.moneyflow

import android.util.Log

actual fun debugLog(tag: String, message: String) {
    Log.d(tag, message)
}

actual fun printThrowable(t: Throwable) {
    t.printStackTrace()
}
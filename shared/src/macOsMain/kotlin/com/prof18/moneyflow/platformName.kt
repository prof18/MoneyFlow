package com.prof18.moneyflow

import platform.Foundation.NSLog

actual fun debugLog(tag: String, message: String) {
    if (Platform.isDebugBinary) {
        NSLog("%s: %s", tag, message)
    }
}

actual fun printThrowable(t: Throwable) {
    t.printStackTrace()
}
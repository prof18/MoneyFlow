package com.prof18.moneyflow.platform

import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

actual interface LocalizedStringProvider {
    actual fun get(id: String): String = id.localized()
    actual fun get(id: String, vararg formatArgs: Any): String = id.localized(*formatArgs)
}

fun String.localized(): String {
    val localizedString = NSBundle.mainBundle.localizedStringForKey(this, this, null)
    return if (localizedString != this) {
        localizedString
    } else {
        this
    }
}

@Suppress("MagicNumber")
fun String.localized(vararg arguments: Any?): String {
    val format = localized().replace("%s", "%@")
    // Kotlin does not support passing variadic parameters to Objective-C
    return when (arguments.size) {
        0 -> NSString.stringWithFormat(format)
        1 -> NSString.stringWithFormat(format, arguments[0])
        2 -> NSString.stringWithFormat(format, arguments[0], arguments[1])
        3 -> NSString.stringWithFormat(format, arguments[0], arguments[1], arguments[2])
        else -> error("Too many arguments.")
    }
}

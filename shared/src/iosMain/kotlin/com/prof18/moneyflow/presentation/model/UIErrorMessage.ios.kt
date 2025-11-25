package com.prof18.moneyflow.presentation.model

import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

actual fun UIErrorMessage.localizedMessage(): String = resolve(messageKey, messageArgs)

actual fun UIErrorMessage.localizedNerdMessage(): String {
    if (nerdMessageArgs.isEmpty()) return ""
    return resolve(nerdMessageKey, nerdMessageArgs)
}

@Suppress("MagicNumber")
private fun resolve(key: String, args: List<String>): String {
    val localized = NSBundle.mainBundle.localizedStringForKey(key, key, null)
    val format = localized.replace("%s", "%@")
    return when (args.size) {
        0 -> format
        1 -> NSString.stringWithFormat(format, args[0])
        2 -> NSString.stringWithFormat(format, args[0], args[1])
        3 -> NSString.stringWithFormat(format, args[0], args[1], args[2])
        else -> NSString.stringWithFormat(format, *args.toTypedArray())
    }
}

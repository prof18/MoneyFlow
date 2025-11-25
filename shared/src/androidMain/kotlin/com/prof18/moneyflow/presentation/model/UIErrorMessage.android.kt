package com.prof18.moneyflow.presentation.model

import android.content.Context
import org.koin.java.KoinJavaComponent.getKoin

actual fun UIErrorMessage.localizedMessage(): String = resolve(messageKey, messageArgs)

actual fun UIErrorMessage.localizedNerdMessage(): String {
    if (nerdMessageArgs.isEmpty()) return ""
    return resolve(nerdMessageKey, nerdMessageArgs)
}

private fun resolve(key: String, args: List<String>): String {
    val context: Context = getKoin().get()
    val resId = context.resources.getIdentifier(key, "string", context.packageName)
    if (resId == 0) return key
    return if (args.isEmpty()) {
        context.getString(resId)
    } else {
        context.getString(resId, *args.toTypedArray())
    }
}

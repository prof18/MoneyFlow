package com.prof18.moneyflow.platform

import android.content.Context
import org.koin.java.KoinJavaComponent.getKoin

actual interface LocalizedStringProvider {

    actual fun get(id: String): String {
        val context: Context = getKoin().get()
        val resourceId = context.resources.getIdentifier(id, "string", context.packageName)
        if (resourceId == 0) {
            return id
        }
        return context.getString(resourceId)
    }

    actual fun get(id: String, vararg formatArgs: Any): String {
        val context: Context = getKoin().get()
        val resourceId = context.resources.getIdentifier(id, "string", context.packageName)
        if (resourceId == 0) {
            return id
        }
        return context.resources.getString(resourceId, *formatArgs)
    }
}

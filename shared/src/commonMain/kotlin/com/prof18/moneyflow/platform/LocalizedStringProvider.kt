package com.prof18.moneyflow.platform

expect class LocalizedStringProvider() {
    fun get(id: String): String
    fun get(id: String, vararg formatArgs: Any): String
}

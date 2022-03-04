package com.prof18.moneyflow.platform

expect interface LocalizedStringProvider {
    open fun get(id: String): String
    open fun get(id: String, vararg formatArgs: Any): String
}

class LocalizedStringProviderImpl : LocalizedStringProvider

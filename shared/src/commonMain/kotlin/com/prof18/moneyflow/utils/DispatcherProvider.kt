package com.prof18.moneyflow.utils

import kotlinx.coroutines.CoroutineDispatcher

internal interface DispatcherProvider {
    fun main(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
    fun unconfined(): CoroutineDispatcher
}
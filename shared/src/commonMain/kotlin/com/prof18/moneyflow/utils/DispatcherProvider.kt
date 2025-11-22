package com.prof18.moneyflow.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatcherProvider {
    fun default(): CoroutineDispatcher = Dispatchers.Default
    fun main(): CoroutineDispatcher = Dispatchers.Main
    fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}

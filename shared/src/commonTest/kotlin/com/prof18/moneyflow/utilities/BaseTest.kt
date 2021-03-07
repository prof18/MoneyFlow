package com.prof18.moneyflow.utilities

import kotlinx.coroutines.CoroutineScope

expect abstract class BaseTest() {
    fun <T> runTest(block: suspend CoroutineScope.() -> T)
}
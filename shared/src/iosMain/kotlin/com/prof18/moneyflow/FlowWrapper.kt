package com.prof18.moneyflow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.launchIn
import kotlin.native.concurrent.freeze

// From https://github.com/russhwolf/To-Do/blob/master/shared/src/iosMain/kotlin/com/russhwolf/todo/shared/CoroutineAdapters.kt
class FlowWrapper<T : Any>(
    private val scope: CoroutineScope,
    private val flow: Flow<T>
) {
    init {
        freeze()
    }

    fun subscribe(
        onEvent: (T) -> Unit,
        onError: (Throwable) -> Unit,
        onComplete: () -> Unit
    ): Job =
        flow
            .onEach { onEvent(it.freeze()) }
            .catch { onError(it.freeze()) }
            .onCompletion { onComplete() }
            .launchIn(scope)
}
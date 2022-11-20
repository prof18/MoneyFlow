package com.prof18.moneyflow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

// From https://github.com/russhwolf/To-Do/blob/master/shared/src/iosMain/kotlin/com/russhwolf/todo/shared/CoroutineAdapters.kt
class FlowWrapper<T : Any>(
    private val scope: CoroutineScope,
    private val flow: Flow<T>,
) {

    fun subscribe(
        onEvent: (T) -> Unit,
        onError: (Throwable) -> Unit,
        onComplete: () -> Unit,
    ): Job =
        flow
            .onEach { onEvent(it) }
            .catch { onError(it) }
            .onCompletion { onComplete() }
            .launchIn(scope)
}

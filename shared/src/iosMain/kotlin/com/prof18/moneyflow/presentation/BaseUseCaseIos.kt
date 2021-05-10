package com.prof18.moneyflow.presentation

import co.touchlab.stately.freeze
import kotlinx.coroutines.*

abstract class BaseUseCaseIos {

    // For testing, make internal
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
    internal val scope = CoroutineScope(SupervisorJob() + dispatcher)

    init {
        freeze()
    }

    fun onDestroy() {
        scope.cancel()
    }

}
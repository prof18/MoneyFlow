package com.prof18.moneyflow

import androidx.lifecycle.get
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

/**
 * Returns a property delegate to access the wrapped value, which will be retained for the
 * duration of the lifecycle of this [ViewModelStoreOwner].
 *
 * ```
 * class MyFragment : Fragment() {
 *   private val presenter by scoped { MyPresenter() }
 *
 *   override fun onCreate(savedInstanceState: Bundle?) {
 *     super.onCreate(savedInstanceState)
 *     presenter.models.collect {
 *       // ...
 *     }
 *   }
 * }
 * ```
 */
inline fun <reified T> ViewModelStoreOwner.scoped(noinline creator: () -> T): Lazy<T> {
    return LazyScopedValue({ viewModelStore }, { ScopeViewModel.Factory(creator) })
}

class ScopeViewModel<V>(
    val value: V
) : ViewModel() {
    class Factory<V>(val valueFactory: () -> V) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            ScopeViewModel(valueFactory()) as? T
                ?: throw java.lang.IllegalArgumentException("Unknown type")
    }
}

class LazyScopedValue<T>(
    private val storeProducer: () -> ViewModelStore,
    private val factoryProducer: () -> ViewModelProvider.Factory
) : Lazy<T> {
    private var cached: Any = NotSet

    @Suppress("UNCHECKED_CAST")
    override val value: T
        get() {
            val value = cached
            return if (value == NotSet) {
                val factory = factoryProducer()
                val store = storeProducer()
                val viewModel = ViewModelProvider(store, factory).get<ScopeViewModel<T>>()

                viewModel.value.also {
                    cached = it as Any
                }
            } else {
                value as T
            }
        }

    override fun isInitialized() = cached != NotSet

    companion object {
        private val NotSet = Any()
    }
}
package com.prof18.moneyflow.presentation.categories

import com.prof18.moneyflow.domain.repository.MoneyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.prof18.moneyflow.printThrowable

class CategoriesUseCaseImpl(
    private val moneyRepository: MoneyRepository,
    // That's only for iOs
    private val viewUpdate: ((CategoryModel) -> Unit)? = null,
) : CategoriesUseCase {

    // Used only on iOs
    private val coroutineScope: CoroutineScope = MainScope()

    private val categoryModel  = MutableStateFlow<CategoryModel>(CategoryModel.Loading)

    override fun observeCategories(): StateFlow<CategoryModel> = categoryModel

    override fun getCategories() {
        coroutineScope.launch {
            getCategoriesSuspendable()
        }
    }

    override suspend fun getCategoriesSuspendable() {
        moneyRepository.getCategories()
            .catch { cause: Throwable ->
                printThrowable(cause)
                // TODO: move to error code
                val error = CategoryModel.Error("Something wrong during the get of category")
                categoryModel.value = error
                viewUpdate?.invoke(error)
            }
            .collect {
                val categoryState = CategoryModel.CategoryState(it)
                categoryModel.value = categoryState
                viewUpdate?.invoke(categoryState)
            }
    }

    // iOs only
    override fun onDestroy() {
        coroutineScope.cancel()
    }

}

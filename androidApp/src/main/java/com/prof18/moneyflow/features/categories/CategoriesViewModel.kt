package com.prof18.moneyflow.features.categories

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.presentation.categories.CategoriesUseCase
import com.prof18.moneyflow.presentation.categories.CategoryModel
import com.prof18.moneyflow.utils.logError
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

internal class CategoriesViewModel(
    private val categoriesUseCase: CategoriesUseCase,
    private val errorMapper: MoneyFlowErrorMapper,
) : ViewModel() {

    var categoryModel: CategoryModel by mutableStateOf(CategoryModel.Loading)
        private set

    init {
        observeCategoryModel()
    }

    private fun observeCategoryModel() {
        viewModelScope.launch {
            categoriesUseCase.observeCategoryModel()
                .catch { throwable: Throwable ->
                    val error = MoneyFlowError.GetCategories(throwable)
                    throwable.logError(error)
                    val errorMessage = errorMapper.getUIErrorMessage(error)
                    emit(CategoryModel.Error(errorMessage))
                }
                .collect {
                    categoryModel = it
                }
        }
    }
}

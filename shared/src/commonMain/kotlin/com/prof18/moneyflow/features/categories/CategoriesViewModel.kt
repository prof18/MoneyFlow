package com.prof18.moneyflow.features.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof18.moneyflow.data.MoneyRepository
import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.presentation.MoneyFlowErrorMapper
import com.prof18.moneyflow.presentation.categories.CategoryModel
import com.prof18.moneyflow.utils.logError
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

internal class CategoriesViewModel(
    private val moneyRepository: MoneyRepository,
    private val errorMapper: MoneyFlowErrorMapper,
) : ViewModel() {

    val categories: StateFlow<CategoryModel> = moneyRepository.getCategories()
        .map { CategoryModel.CategoryState(it) as CategoryModel }
        .catch { throwable: Throwable ->
            val error = MoneyFlowError.GetCategories(throwable)
            throwable.logError(error)
            emit(CategoryModel.Error(errorMapper.getUIErrorMessage(error)))
        }.stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5_000),
            initialValue = CategoryModel.Loading,
        )
}

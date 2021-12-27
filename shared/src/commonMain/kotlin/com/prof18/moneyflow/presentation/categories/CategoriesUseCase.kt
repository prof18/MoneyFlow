package com.prof18.moneyflow.presentation.categories

import com.prof18.moneyflow.domain.entities.MoneyFlowError
import com.prof18.moneyflow.domain.repository.MoneyRepository
import com.prof18.moneyflow.utils.logError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class CategoriesUseCase(
    private val moneyRepository: MoneyRepository,
) {

    fun observeCategoryModel(): Flow<CategoryModel> =
        moneyRepository.getCategories()
            .catch { throwable: Throwable ->
                val error = MoneyFlowError.GetCategories(throwable)
                throwable.logError(error)
                CategoryModel.Error(error)
            }.map {
                CategoryModel.CategoryState(it)
            }
}
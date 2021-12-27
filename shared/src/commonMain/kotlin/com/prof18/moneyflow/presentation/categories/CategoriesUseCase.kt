package com.prof18.moneyflow.presentation.categories

import co.touchlab.kermit.Logger
import com.prof18.moneyflow.domain.repository.MoneyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class CategoriesUseCase(
    private val moneyRepository: MoneyRepository,
) {

    fun observeCategoryModel(): Flow<CategoryModel> =
        moneyRepository.getCategories()
            .catch { cause: Throwable ->
                Logger.w(cause) { "Error while getting Categories" }
                // TODO: move to error code
                CategoryModel.Error("Something wrong during the get of category")
            }.map {
                CategoryModel.CategoryState(it)
            }
}
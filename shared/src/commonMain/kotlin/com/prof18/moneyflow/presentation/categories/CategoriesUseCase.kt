package com.prof18.moneyflow.presentation.categories

import com.prof18.moneyflow.domain.repository.MoneyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.native.HiddenFromObjC

@HiddenFromObjC
class CategoriesUseCase(
    private val moneyRepository: MoneyRepository,
) {
    fun observeCategoryModel(): Flow<CategoryModel> =
        moneyRepository.getCategories().map {
            CategoryModel.CategoryState(it)
        }
}

package com.prof18.moneyflow.presentation.categories

import com.prof18.moneyflow.domain.repository.MoneyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.native.HiddenFromObjC
import kotlin.native.ObjCName

@ObjCName("_CategoriesUseCase")
class CategoriesUseCase(
    private val moneyRepository: MoneyRepository,
) {

    @HiddenFromObjC
    fun observeCategoryModel(): Flow<CategoryModel> =
        moneyRepository.getCategories().map {
            CategoryModel.CategoryState(it)
        }
}

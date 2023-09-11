package com.prof18.moneyflow.presentation

import com.prof18.moneyflow.FlowWrapper
import com.prof18.moneyflow.presentation.categories.CategoriesUseCase
import com.prof18.moneyflow.presentation.categories.CategoryModel

@ObjCName("CategoriesUseCase")
class CategoriesUseCaseIos(
    private val categoriesUseCase: CategoriesUseCase,
) : BaseUseCaseIos() {

    fun getCategories(): FlowWrapper<CategoryModel> =
        FlowWrapper(scope, categoriesUseCase.observeCategoryModel())
}

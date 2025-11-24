package com.prof18.moneyflow.presentation.categories

import com.prof18.moneyflow.domain.entities.Category
import com.prof18.moneyflow.presentation.model.UIErrorMessage

sealed class CategoryModel {
    object Loading : CategoryModel()
    data class Error(val uiErrorMessage: UIErrorMessage) : CategoryModel()
    data class CategoryState(val categories: List<Category>) : CategoryModel()
}

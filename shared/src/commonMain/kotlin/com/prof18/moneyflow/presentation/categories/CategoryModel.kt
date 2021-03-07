package com.prof18.moneyflow.presentation.categories

import com.prof18.moneyflow.domain.entities.Category

sealed class CategoryModel {
    object Loading: CategoryModel()
    data class Error(val message: String): CategoryModel()
    data class CategoryState(val categories: List<Category>): CategoryModel()
}
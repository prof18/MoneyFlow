package presentation.categories

import domain.model.Category

sealed class CategoryModel {
    object Loading: CategoryModel()
    data class Error(val message: String): CategoryModel()
    data class CategoryState(val categories: List<Category>): CategoryModel()
}
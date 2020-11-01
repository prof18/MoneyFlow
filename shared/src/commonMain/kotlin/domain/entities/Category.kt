package domain.entities

import presentation.CategoryIcon

data class Category(
    val id: Long,
    val name: String,
    val icon: CategoryIcon
)
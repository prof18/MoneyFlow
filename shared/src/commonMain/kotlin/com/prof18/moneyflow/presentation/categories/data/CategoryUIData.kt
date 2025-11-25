package com.prof18.moneyflow.presentation.categories.data

import com.prof18.moneyflow.domain.entities.Category
import com.prof18.moneyflow.presentation.model.CategoryIcon
import kotlinx.serialization.Serializable

@Serializable
internal data class CategoryUIData(
    val id: Long,
    val name: String,
    val icon: CategoryIcon,
)

internal fun Category.toCategoryUIData() = CategoryUIData(
    id = this.id,
    name = this.name,
    icon = this.icon,
)

package com.prof18.moneyflow.features.categories.data

import android.os.Parcelable
import com.prof18.moneyflow.domain.entities.Category
import com.prof18.moneyflow.presentation.model.CategoryIcon
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class CategoryUIData(
    val id: Long,
    val name: String,
    val icon: CategoryIcon,
) : Parcelable

internal fun Category.toCategoryUIData() = CategoryUIData(
    id = this.id,
    name = this.name,
    icon = this.icon,
)

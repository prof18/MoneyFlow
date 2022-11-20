package com.prof18.moneyflow.features.categories.data

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.prof18.moneyflow.domain.entities.Category
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class CategoryUIData(
    val id: Long,
    val name: String,
    @DrawableRes val icon: Int,
) : Parcelable

internal fun Category.toCategoryUIData() = CategoryUIData(
    id = this.id,
    name = this.name,
    icon = this.icon.mapToAndroidIcon(),
)

package com.prof18.moneyflow.features.categories.data

import android.os.Parcelable
import domain.model.Category
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryUIData(
    val id: Long,
    val name: String
    // TODO: add icon
) : Parcelable

fun Category.toCategoryUIData() = CategoryUIData(
    id = this.id,
    name = this.name
)
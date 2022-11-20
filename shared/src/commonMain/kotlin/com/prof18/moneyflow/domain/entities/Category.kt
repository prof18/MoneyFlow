package com.prof18.moneyflow.domain.entities

import com.prof18.moneyflow.presentation.model.CategoryIcon

data class Category(
    val id: Long,
    val name: String,
    val icon: CategoryIcon,
)

package com.prof18.moneyflow.features.categories.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.prof18.moneyflow.features.categories.data.mapToAndroidIcon
import com.prof18.moneyflow.ui.style.AppColors
import com.prof18.moneyflow.ui.style.AppMargins
import domain.model.Category
import presentation.CategoryIcon

@Composable
fun CategoryCard(
    category: Category,
    onClick: ((Category) -> Unit)?
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.clickable(onClick = {
            onClick?.invoke(category)
        }, enabled = onClick != null)
    ) {

        Row(modifier = Modifier.weight(8f)) {

            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(
                        AppMargins.regular,
                    )
                    // TODO: find a better color for the dark mode
                    .background(AppColors.darkGrey, shape = RoundedCornerShape(AppMargins.regularCornerRadius))
                ) {
                Icon(
                    vectorResource(id = category.icon.mapToAndroidIcon()),
                    modifier = Modifier.padding(AppMargins.small)
                )
            }

            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = category.name,
                style = MaterialTheme.typography.subtitle1
            )
        }
    }


}

@Preview
@Composable
fun CategoryCardPreview() {
    return CategoryCard(
        category = Category(id = 11, name = "Family", icon = CategoryIcon.IC_QUESTION_CIRCLE),
        onClick = {}
    )
}
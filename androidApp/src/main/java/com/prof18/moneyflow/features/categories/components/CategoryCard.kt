package com.prof18.moneyflow.features.categories.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prof18.moneyflow.features.categories.data.mapToAndroidIcon
import com.prof18.moneyflow.ui.style.AppMargins
import com.prof18.moneyflow.domain.entities.Category
import com.prof18.moneyflow.presentation.CategoryIcon

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
                    .background(
                        MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(AppMargins.regularCornerRadius)
                    )
            ) {
                Icon(
                    painter = painterResource(id = category.icon.mapToAndroidIcon()),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(AppMargins.small)
                        .size(28.dp),
                    tint = MaterialTheme.colors.onPrimary
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
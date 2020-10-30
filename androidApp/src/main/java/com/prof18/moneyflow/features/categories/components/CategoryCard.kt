package com.prof18.moneyflow.features.categories.components

import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import com.prof18.moneyflow.R
import com.prof18.moneyflow.ui.style.AppColors
import com.prof18.moneyflow.ui.style.AppMargins
import domain.model.Category

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
                    ),
                // TODO: find a better color for the dark mode
                backgroundColor = AppColors.darkGrey,
                shape = RoundedCornerShape(AppMargins.regularCornerRadius),
            ) {
                Modifier.align(Alignment.CenterVertically)
                    .padding(AppMargins.small)
                Icon(vectorResource(id = R.drawable.ic_hamburger_solid))
            }

            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = category.name,
                style = MaterialTheme.typography.subtitle1
            )

//            Column(
//                modifier = Modifier.align(Alignment.CenterVertically).padding(
//                    top = AppMargins.regular,
//                    bottom = AppMargins.regular,
//                    end = AppMargins.regular,
//                ),
//            ) {
//
//
//
//                Text(
//                    text = transaction.formattedDate,
//                    style = MaterialTheme.typography.caption
//                )
//            }
        }
    }


}

@Preview
@Composable
fun CategoryCardPreview() {
    return CategoryCard(
        category = Category(id = 11, name = "Family"),
        onClick = {}
    )
}
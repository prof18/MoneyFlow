package com.prof18.moneyflow.features.categories.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prof18.moneyflow.domain.entities.Category
import com.prof18.moneyflow.features.categories.data.mapToAndroidIcon
import com.prof18.moneyflow.presentation.model.CategoryIcon
import com.prof18.moneyflow.ui.style.Margins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme

@Composable
internal fun CategoryCard(
    category: Category,
    onClick: ((Category) -> Unit)?,
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.clickable(onClick = {
            onClick?.invoke(category)
        }, enabled = onClick != null),
    ) {

        // TODO is this weight necessary?
        @Suppress("MagicNumber")
        Row(modifier = Modifier.weight(8f)) {

            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(
                        Margins.regular,
                    )
                    .background(
                        MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(Margins.regularCornerRadius),
                    ),
            ) {
                Icon(
                    painter = painterResource(id = category.icon.mapToAndroidIcon()),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(Margins.small)
                        .size(28.dp),
                    tint = MaterialTheme.colors.onPrimary,
                )
            }

            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = category.name,
                style = MaterialTheme.typography.subtitle1,
            )
        }
    }
}

@Preview(name = "CategoryCard Light")
@Preview(name = "CategoryCard Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CategoryCardPreview() {
    MoneyFlowTheme {
        Surface {
            CategoryCard(
                category = Category(id = 11, name = "Family", icon = CategoryIcon.IC_QUESTION_CIRCLE),
                onClick = {},
            )
        }
    }
}

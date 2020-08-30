package com.prof18.moneyflow.ui.components

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeightIn
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.prof18.moneyflow.style.AppMargins
import com.prof18.moneyflow.style.MoneyFlowTheme

@Composable
fun HeaderNavigator(
    onClick: () -> Unit = {}
) {

    Row(
        verticalGravity = Alignment.CenterVertically,
        modifier = Modifier
            .preferredHeightIn(minHeight = 56.dp)
            .clickable(onClick = {
                onClick()
            })
    ) {
        Text(
            // TODO: localize
            text = "Latest Transactions",
            style = MaterialTheme.typography.h6,
//            color = JetsnackTheme.colors.brand,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
                .padding(start = AppMargins.regular)
        )

        Text(
            // TODO: localize
            text = "See All",
            style = MaterialTheme.typography.subtitle1
        )

        IconButton(
            onClick = { /* todo */ },
            modifier = Modifier.gravity(Alignment.CenterVertically)
        ) {
            Icon(
                asset = Icons.Outlined.KeyboardArrowRight,
//                tint = JetsnackTheme.colors.brand
            )
        }
    }
}

@Preview("Header Navigator - light theme")
@Composable
fun SnackCardPreview() {
    MoneyFlowTheme {
        HeaderNavigator()
    }
}

@Preview("Header Navigator - dark theme")
@Composable
fun SnackCardDarkPreview() {
    MoneyFlowTheme(darkTheme = true) {
        HeaderNavigator()
    }
}
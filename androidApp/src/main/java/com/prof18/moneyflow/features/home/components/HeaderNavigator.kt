package com.prof18.moneyflow.features.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prof18.moneyflow.ui.style.AppMargins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme

@Composable
fun HeaderNavigator(
    onClick: () -> Unit = {}
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = AppMargins.regular, bottom = AppMargins.small)
            .clickable(onClick = {
                onClick()
            })
    ) {
        Text(
            // TODO: localize
            text = "Latest Transactions",
            style = MaterialTheme.typography.h5,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
                .padding(start = AppMargins.regular)
        )


        IconButton(
            onClick = { /* todo */ },
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(vertical = AppMargins.small)        ) {
            Icon(
                Icons.Outlined.KeyboardArrowRight,
                contentDescription = null
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
package com.prof18.moneyflow.features.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.prof18.moneyflow.R
import com.prof18.moneyflow.ui.style.AppMargins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme

@Preview("Header Navigator - light theme")
@Composable
fun SnackCardPreview() {
    MoneyFlowTheme {
        Surface {
            HeaderNavigator()
        }
    }
}

@Preview("Header Navigator - dark theme")
@Composable
fun SnackCardDarkPreview() {
    MoneyFlowTheme(darkTheme = true) {
        Surface {
            HeaderNavigator()
        }
    }
}

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
            .clickable { onClick() }
    ) {
        Text(
            text = stringResource(R.string.latest_transactions),
            style = MaterialTheme.typography.h5,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
                .padding(start = AppMargins.regular)
        )

        IconButton(
            onClick = { /* no-op, managed by the row */ },
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(vertical = AppMargins.small)
        ) {
            Icon(
                Icons.Outlined.KeyboardArrowRight,
                contentDescription = null
            )
        }
    }
}

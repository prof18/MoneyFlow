package com.prof18.moneyflow.features.home.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.prof18.moneyflow.ui.style.Margins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme

@Composable
internal fun HeaderNavigator(
    title: String,
    onClick: () -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Margins.regular, bottom = Margins.small)
            .clickable { onClick() },
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h5,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
                .padding(start = Margins.regular),
        )

        IconButton(
            onClick = { /* no-op, managed by the row */ },
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(vertical = Margins.small),
        ) {
            Icon(
                Icons.Outlined.KeyboardArrowRight,
                contentDescription = null,
            )
        }
    }
}

@Preview(name = "HeaderNavigator Light")
@Preview(name = "HeaderNavigator Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SnackCardPreview() {
    MoneyFlowTheme {
        Surface {
            HeaderNavigator(title = "This is a title")
        }
    }
}

package com.prof18.moneyflow.features.addtransaction.components

import androidx.compose.material.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import com.prof18.moneyflow.R
import com.prof18.moneyflow.ui.style.AppMargins

@Composable
fun IconTextClickableRow(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    iconId: Int,
    isSomethingSelected: Boolean = true
) {
    Column(modifier = modifier.clickable(onClick = onClick)) {

        Row {

            Icon(
                vectorResource(id = iconId),
                modifier = Modifier.padding(start = AppMargins.horizontalIconPadding),
                tint = MaterialTheme.colors.onSurface.copy(alpha = 0.54f)
            )

            Spacer(Modifier.preferredWidth(AppMargins.textFieldPadding))

            Text(
                text,
                style = MaterialTheme.typography.body1,
                color = if (isSomethingSelected) Color.Black else Color.Black.copy(alpha = 0.5f),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )

        }

        Spacer(Modifier.height(AppMargins.small))

        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f))

    }
}

@Preview
@Composable
fun IconTextClickableRowPreview() {
    return IconTextClickableRow(
        onClick = {},
        text = "Select something",
        iconId = R.drawable.ic_question_circle,
    )
}
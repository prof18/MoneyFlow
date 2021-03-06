package com.prof18.moneyflow.features.addtransaction.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prof18.moneyflow.R
import com.prof18.moneyflow.ui.style.AppMargins
import com.prof18.moneyflow.ui.style.textColor

@Composable
fun IconTextClickableRow(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    iconId: Int,
    isSomethingSelected: Boolean = true
) {
    Column(modifier = modifier
        .border(
            BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.3f)),
            RoundedCornerShape(4.dp)
        )
        .clickable(onClick = onClick)
        .fillMaxWidth()
        .padding(vertical = 12.dp)
    ) {

        Row {

            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = textColor(),
                modifier = Modifier.padding(start = AppMargins.horizontalIconPadding),
            )

            Spacer(Modifier.width(AppMargins.textFieldPadding))

            Text(
                text,
                style = MaterialTheme.typography.body1,
                color = if (isSomethingSelected) textColor() else textColor().copy(alpha = 0.5f),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )

        }

//        Spacer(Modifier.height(AppMargins.small))

//        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f))

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
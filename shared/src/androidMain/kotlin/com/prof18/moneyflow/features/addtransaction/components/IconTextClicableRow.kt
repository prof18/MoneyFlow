package com.prof18.moneyflow.features.addtransaction.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prof18.moneyflow.R
import com.prof18.moneyflow.ui.style.Margins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme

@Composable
internal fun IconTextClickableRow(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    iconId: Int,
    isSomethingSelected: Boolean = true,
) {
    Column(
        modifier = modifier
            .border(
                BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.3f)),
                RoundedCornerShape(4.dp),
            )
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .padding(vertical = 12.dp),
    ) {

        Row {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "$text ${stringResource(id = R.string.icon_content_desc)}",
                tint = if (isSystemInDarkTheme()) {
                    Color(color = 0xff888a8f)
                } else {
                    Color(color = 0xff8d989d)
                },
                modifier = Modifier.padding(start = Margins.horizontalIconPadding),
            )

            Spacer(Modifier.width(Margins.textFieldPadding))

            @Suppress("MagicNumber")
            val alpha = if (isSomethingSelected) {
                1.0f
            } else {
                0.5f
            }

            Text(
                text,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .alpha(alpha)
                    .align(Alignment.CenterVertically),
            )
        }
    }
}

@Preview(name = "IconTextClickableRow Light")
@Preview(name = "IconTextClickableRow Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun IconTextClickableRowPreview() {
    MoneyFlowTheme {
        Surface {
            IconTextClickableRow(
                onClick = {},
                text = "Select something",
                iconId = R.drawable.ic_question_circle,
            )
        }
    }
}

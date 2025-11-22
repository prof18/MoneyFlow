package com.prof18.moneyflow.ui.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.prof18.moneyflow.R
import com.prof18.moneyflow.ui.style.Margins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import com.prof18.moneyflow.ui.style.upArrowCircleColor
import com.prof18.moneyflow.ui.style.upArrowColor

@Composable
internal fun ArrowCircleIcon(
    boxColor: Color,
    @DrawableRes iconID: Int,
    arrowColor: Color,
    iconSize: Dp,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                boxColor,
                shape = CircleShape,
            ),
    ) {
        Icon(
            painter = painterResource(id = iconID),
            contentDescription = null,
            modifier = Modifier
                .padding(Margins.small)
                .size(iconSize),
            tint = arrowColor,
        )
    }
}

@Preview(name = "ArrowCircleIcon Light")
@Preview(name = "ArrowCircleIcon Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ArrowCircleIconPreview() {
    Surface {
        MoneyFlowTheme {
            ArrowCircleIcon(
                boxColor = upArrowCircleColor(),
                iconID = R.drawable.ic_arrow_up_rotate,
                arrowColor = upArrowColor(),
                iconSize = 18.dp,
            )
        }
    }
}

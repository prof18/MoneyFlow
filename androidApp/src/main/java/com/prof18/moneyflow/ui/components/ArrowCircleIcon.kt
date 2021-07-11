package com.prof18.moneyflow.ui.components

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
import com.prof18.moneyflow.ui.style.AppMargins
import com.prof18.moneyflow.ui.style.MoneyFlowTheme
import com.prof18.moneyflow.ui.style.upArrowCircleColor
import com.prof18.moneyflow.ui.style.upArrowColor

@Preview
@Composable
fun ArrowCircleIconPreview() {
    Surface {
        MoneyFlowTheme {
            ArrowCircleIcon(
                boxColor = upArrowCircleColor(),
                iconID = R.drawable.ic_arrow_up_rotate,
                arrowColor = upArrowColor(),
                iconSize = 18.dp
            )
        }
    }
}

@Composable
fun ArrowCircleIcon(
    boxColor: Color,
    @DrawableRes iconID: Int,
    arrowColor: Color,
    iconSize: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                boxColor,
                shape = CircleShape
            )
    ) {
        Icon(
            painter = painterResource(id = iconID),
            contentDescription = null,
            modifier = Modifier
                .padding(AppMargins.small)
                .size(iconSize),
            tint = arrowColor
        )
    }
}

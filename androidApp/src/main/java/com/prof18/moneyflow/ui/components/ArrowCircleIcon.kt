package com.prof18.moneyflow.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.prof18.moneyflow.ui.style.AppMargins

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
            .padding(end = AppMargins.regular)
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
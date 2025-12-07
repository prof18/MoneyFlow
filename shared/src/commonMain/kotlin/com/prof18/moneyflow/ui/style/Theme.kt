package com.prof18.moneyflow.ui.style

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme as Material2Theme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
internal fun upArrowCircleColor(): Color = if (isSystemInDarkTheme()) {
    ExpressiveExtendedColors.positiveContainer
} else {
    ExpressiveExtendedColors.positiveContainer
}

@Composable
internal fun upArrowColor(): Color = if (isSystemInDarkTheme()) {
    ExpressiveExtendedColors.positiveOnContainer
} else {
    ExpressiveExtendedColors.positive
}

@Composable
internal fun downArrowCircleColor(): Color = if (isSystemInDarkTheme()) {
    ExpressiveExtendedColors.negativeContainer
} else {
    ExpressiveExtendedColors.negativeContainer
}

@Composable
internal fun downArrowColor(): Color = if (isSystemInDarkTheme()) {
    ExpressiveExtendedColors.negativeOnContainer
} else {
    ExpressiveExtendedColors.negative
}

@Composable
fun MoneyFlowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) expressiveDarkColorScheme else expressiveLightColorScheme
    val typography = moneyFlowTypography()

    val materialColors = if (darkTheme) {
        darkColors(
            primary = colorScheme.primary,
            primaryVariant = colorScheme.primaryContainer,
            secondary = colorScheme.secondary,
            background = colorScheme.background,
            surface = colorScheme.surface,
            error = colorScheme.error,
            onPrimary = colorScheme.onPrimary,
            onSecondary = colorScheme.onSecondary,
            onBackground = colorScheme.onBackground,
            onSurface = colorScheme.onSurface,
            onError = colorScheme.onError,
        )
    } else {
        lightColors(
            primary = colorScheme.primary,
            primaryVariant = colorScheme.primaryContainer,
            secondary = colorScheme.secondary,
            background = colorScheme.background,
            surface = colorScheme.surface,
            error = colorScheme.error,
            onPrimary = colorScheme.onPrimary,
            onSecondary = colorScheme.onSecondary,
            onBackground = colorScheme.onBackground,
            onSurface = colorScheme.onSurface,
            onError = colorScheme.onError,
        )
    }

    val materialTypography = androidx.compose.material.Typography(
        h1 = typography.h1,
        h2 = typography.h2,
        h3 = typography.h3,
        h4 = typography.h4,
        h5 = typography.h5,
        h6 = typography.h6,
        subtitle1 = typography.subtitle1,
        subtitle2 = typography.subtitle2,
        body1 = typography.body1,
        body2 = typography.body2,
        button = typography.button,
        caption = typography.caption,
        overline = typography.overline,
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        shapes = MoneyFlowShapes,
    ) {
        Material2Theme(
            colors = materialColors,
            typography = materialTypography,
            shapes = MoneyFlowLegacyShapes,
            content = content,
        )
    }
}

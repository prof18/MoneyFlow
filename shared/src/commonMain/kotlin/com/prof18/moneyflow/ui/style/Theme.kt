package com.prof18.moneyflow.ui.style

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = LightAppColors.primary,
    // secondary = LightAppColors.yellow1,
    background = LightAppColors.background,
    error = LightAppColors.red1,
    onPrimary = LightAppColors.lightGrey,
    onSecondary = DarkAppColors.gray4,
    onBackground = DarkAppColors.gray4,
    onSurface = DarkAppColors.gray4,
    onError = DarkAppColors.gray4,
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkAppColors.primary,
    // secondary = DarkAppColors.yellow1,
    error = DarkAppColors.red1,
    onPrimary = LightAppColors.gray4,
    onSecondary = LightAppColors.gray4,
    onBackground = LightAppColors.gray4,
    onSurface = LightAppColors.gray4,
    onError = LightAppColors.gray4,
)

@Composable
internal fun upArrowCircleColor(): Color = if (isSystemInDarkTheme()) DarkAppColors.green3 else LightAppColors.green3

@Composable
internal fun upArrowColor(): Color = if (isSystemInDarkTheme()) LightAppColors.green3 else LightAppColors.green1

@Composable
internal fun downArrowCircleColor(): Color = if (isSystemInDarkTheme()) DarkAppColors.red3 else LightAppColors.red3

@Composable
internal fun downArrowColor(): Color = if (isSystemInDarkTheme()) LightAppColors.red3 else LightAppColors.red1

@Composable
fun MoneyFlowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = moneyFlowTypography(),
        shapes = MoneyFlowShapes,
        content = content,
    )
}

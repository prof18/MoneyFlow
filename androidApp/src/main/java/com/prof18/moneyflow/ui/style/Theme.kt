package com.prof18.moneyflow.ui.style

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightThemeColors = lightColors(
    primary = LightAppColors.green1,
    primaryVariant = LightAppColors.green2,
    secondary = LightAppColors.yellow1,
    secondaryVariant = LightAppColors.yellow2,

    background = LightAppColors.background,
    surface = LightAppColors.gray4,
    error = LightAppColors.red1,

    onPrimary = DarkAppColors.gray4,
    onSecondary = DarkAppColors.gray4,
    onBackground = DarkAppColors.gray4,
    onSurface = DarkAppColors.gray4,
    onError = DarkAppColors.gray4,
)

private val DarkThemeColors = darkColors(
    primary = DarkAppColors.green1,
    primaryVariant = DarkAppColors.green2,
    secondary = DarkAppColors.yellow1,
    secondaryVariant = DarkAppColors.yellow2,

    background = DarkAppColors.background,
    surface = DarkAppColors.gray4,
    error = DarkAppColors.red1,

    onPrimary = LightAppColors.gray4,
    onSecondary = LightAppColors.gray4,
    onBackground = LightAppColors.gray4,
    onSurface = LightAppColors.gray4,
    onError = LightAppColors.gray4,
)

// TODO: check this colors
@Composable
fun containerColor(): Color = if (isSystemInDarkTheme()) LightAppColors.blue1 else DarkAppColors.blue1

@Composable
fun textColor(): Color = if (isSystemInDarkTheme()) LightAppColors.gray1 else Color.Black

@Composable
fun bigTextColor(): Color = if (isSystemInDarkTheme()) LightAppColors.gray1 else Color.Black.copy(alpha = 0.7f)

@Composable
fun MoneyFlowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors =  if (darkTheme) DarkThemeColors else LightThemeColors,
        typography = MoneyFlowTypography,
        shapes = MoneyFlowShapes,
        content = content
    )
}
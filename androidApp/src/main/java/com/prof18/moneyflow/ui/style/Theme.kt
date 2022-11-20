package com.prof18.moneyflow.ui.style

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightThemeColors = lightColors(
    primary = LightAppColors.primary,
//    primaryVariant = LightAppColors.green2,
//    secondary = LightAppColors.yellow1,
//    secondaryVariant = LightAppColors.yellow2,

    background = LightAppColors.background,
//    surface = LightAppColors.gray4,
    error = LightAppColors.red1,

    onPrimary = LightAppColors.lightGrey,
    onSecondary = DarkAppColors.gray4,
    onBackground = DarkAppColors.gray4,
    onSurface = DarkAppColors.gray4,
    onError = DarkAppColors.gray4,
)

private val DarkThemeColors = darkColors(
    primary = DarkAppColors.primary,
//    primaryVariant = DarkAppColors.green2,
//    secondary = DarkAppColors.yellow1,
//    secondaryVariant = DarkAppColors.yellow2,

//    background = backgroundColorDark,
//    surface = primaryBlueDark, // It's for example for the bottom bar
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
internal fun MoneyFlowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        typography = MoneyFlowTypography,
        shapes = MoneyFlowShapes,
        content = content,
    )
}

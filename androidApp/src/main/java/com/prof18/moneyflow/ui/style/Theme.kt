package com.prof18.moneyflow.ui.style

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

// TODO: add later on
private val LightThemeColors = lightColors(
    primary = AppColors.blue700,
    primaryVariant = AppColors.black800,
    secondary = AppColors.orange500,
    secondaryVariant = AppColors.orange400,

    background = AppColors.blue50,
    surface = AppColors.white50,
    error = AppColors.red400,

    onPrimary = AppColors.white50,
    onSecondary = AppColors.black900,
    onBackground = AppColors.black900,
    onSurface = AppColors.black900,
    onError = AppColors.black900,
)

// TODO: add later on
private val DarkThemeColors = darkColors(
    primary = AppColors.blue200,
    primaryVariant = AppColors.blue300,
    secondary = AppColors.orange300,

    background = AppColors.black900,
    surface = AppColors.black800,
    error = AppColors.red200,

    onPrimary = AppColors.black900,
    onSecondary = AppColors.black900,
    onBackground = AppColors.white50,
    onSurface = AppColors.white50,
    onError = AppColors.black900,
)


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
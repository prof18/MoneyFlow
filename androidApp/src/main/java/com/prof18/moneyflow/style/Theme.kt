package com.prof18.moneyflow.style

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Red200 = Color(0xfff297a2)
val Red300 = Color(0xffea6d7e)
val Red700 = Color(0xffdd0d00)
val Red800 = Color(0xffd00036)
val Red900 = Color(0xffc20029)


// TODO: add later on
private val LightThemeColors = lightColors(
    primary = Red700,
    primaryVariant = Red900,
    onPrimary = Color.White,
    secondary = Red700,
    secondaryVariant = Red900,
    onSecondary = Color.White,
    error = Red800
)

// TODO: add later on
private val DarkThemeColors = darkColors(
    primary = Red300,
    primaryVariant = Red700,
    onPrimary = Color.Black,
    secondary = Red300,
    onSecondary = Color.White,
    error = Red200
)


@Composable
fun MoneyFlowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    Log.d("AAAA", "Dark theme: $darkTheme")


    MaterialTheme(
        colors = if (darkTheme) darkColors() else lightColors(),
        typography = MoneyFlowTypography,
        shapes = MoneyFlowShapes,
        content = content
    )
}
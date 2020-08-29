package com.prof18.moneyflow.style

import androidx.compose.ui.graphics.Color

object AppColors {

    val yellowAccent = hexColor("#FFC700")
    val darkGrey = hexColor("#C4C4C4")
    val lightGrey = hexColor("#F8F5F5")

    // Primary
    val cyan900 = hexColor("#F8F5F5")
    val cyanLight = hexColor("#428e92")
    val cyanDark = hexColor("#00363a")

    // Secondary
    val lightGreen600 = hexColor("#7cb342")
    val lightGreenLight = hexColor("#aee571")
    val lightGreenDark = hexColor("#4b830d")

    fun hexColor(colorString: String): Color {
        return Color(android.graphics.Color.parseColor(colorString))
    }

}
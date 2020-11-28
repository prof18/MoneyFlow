package com.prof18.moneyflow.ui.style

import androidx.compose.ui.graphics.Color

object AppColors {

    val yellowAccent = hexColor("#FFC700")
    val darkGrey = hexColor("#C4C4C4")
    val darkerGrey = hexColor("#848383")
    val lightGrey = hexColor("#F8F5F5")

    // Primary
    val cyan900 = hexColor("#F8F5F5")
    val cyanLight = hexColor("#428e92")
    val cyanDark = hexColor("#00363a")

    // Secondary
    val lightGreen600 = hexColor("#7cb342")
    val lightGreenLight = hexColor("#aee571")
    val lightGreenDark = hexColor("#4b830d")


    val white50 = hexColor("#ffffff")
    val black800 = hexColor("#121212")
    val black900 = hexColor("#000000")
    val blue50 = hexColor("#eef0f2")
    val blue100 = hexColor("#d2dbe0")
    val blue200 = hexColor("#adbbc4")
    val blue300 = hexColor("#8ca2ae")
    val blue600 = hexColor("#4a6572")
    val blue700 = hexColor("#344955")
    val blue800 = hexColor("#232f34")
    val orange300 = hexColor("#fbd790")
    val orange400 = hexColor("#f9be64")
    val orange500 = hexColor("#f9aa33")
    val red200 = hexColor("#cf7779")
    val red400 = hexColor("#ff4c5d")



    private fun hexColor(colorString: String): Color {
        return Color(android.graphics.Color.parseColor(colorString))
    }

}
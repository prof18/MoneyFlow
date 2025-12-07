package com.prof18.moneyflow.ui.style

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val PrimaryPurple = Color(0xFF4E5DDE)
private val OnPrimaryPurple = Color(0xFFFFFFFF)
private val PrimaryContainer = Color(0xFFE0E0FF)
private val OnPrimaryContainer = Color(0xFF10136B)

private val SecondaryTeal = Color(0xFF00A284)
private val OnSecondaryTeal = Color(0xFFFFFFFF)
private val SecondaryContainer = Color(0xFFB5FFE7)
private val OnSecondaryContainer = Color(0xFF002019)

private val TertiaryMagenta = Color(0xFFB02F6E)
private val OnTertiaryMagenta = Color(0xFFFFFFFF)
private val TertiaryContainer = Color(0xFFFFD7E8)
private val OnTertiaryContainer = Color(0xFF3E0025)

private val ExpressiveSurface = Color(0xFFFBF8FF)
private val ExpressiveSurfaceVariant = Color(0xFFE3E1EC)
private val ExpressiveOnSurface = Color(0xFF1B1B23)
private val ExpressiveOnSurfaceVariant = Color(0xFF46464F)
private val ExpressiveOutline = Color(0xFF777680)
private val ExpressiveOutlineVariant = Color(0xFFC7C6D0)

private val InverseSurface = Color(0xFF302F37)
private val InverseOnSurface = Color(0xFFF1EFF7)
private val InversePrimary = Color(0xFFBEC2FF)

private val DarkPrimary = Color(0xFFBEC2FF)
private val DarkOnPrimary = Color(0xFF1D2275)
private val DarkPrimaryContainer = Color(0xFF353C9C)
private val DarkOnPrimaryContainer = Color(0xFFE0E0FF)

private val DarkSecondary = Color(0xFF5CD3BE)
private val DarkOnSecondary = Color(0xFF00382C)
private val DarkSecondaryContainer = Color(0xFF005142)
private val DarkOnSecondaryContainer = Color(0xFFB5FFE7)

private val DarkTertiary = Color(0xFFFFA3D3)
private val DarkOnTertiary = Color(0xFF5E113D)
private val DarkTertiaryContainer = Color(0xFF7B2955)
private val DarkOnTertiaryContainer = Color(0xFFFFD7E8)

private val DarkSurface = Color(0xFF12131A)
private val DarkOnSurface = Color(0xFFE3E1EB)
private val DarkSurfaceVariant = Color(0xFF46464F)
private val DarkOnSurfaceVariant = Color(0xFFC7C6D0)
private val DarkOutline = Color(0xFF91909A)

private val DarkInverseSurface = Color(0xFFE3E1EB)
private val DarkInverseOnSurface = Color(0xFF1B1B23)
private val DarkInversePrimary = Color(0xFF4E5DDE)

internal val expressiveLightColorScheme = lightColorScheme(
    primary = PrimaryPurple,
    onPrimary = OnPrimaryPurple,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    inversePrimary = InversePrimary,
    secondary = SecondaryTeal,
    onSecondary = OnSecondaryTeal,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = TertiaryMagenta,
    onTertiary = OnTertiaryMagenta,
    tertiaryContainer = TertiaryContainer,
    onTertiaryContainer = OnTertiaryContainer,
    background = ExpressiveSurface,
    onBackground = ExpressiveOnSurface,
    surface = ExpressiveSurface,
    onSurface = ExpressiveOnSurface,
    surfaceVariant = ExpressiveSurfaceVariant,
    onSurfaceVariant = ExpressiveOnSurfaceVariant,
    surfaceTint = PrimaryPurple,
    inverseSurface = InverseSurface,
    inverseOnSurface = InverseOnSurface,
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    outline = ExpressiveOutline,
    outlineVariant = ExpressiveOutlineVariant,
    scrim = Color(0xFF000000),
)

internal val expressiveDarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = DarkOnPrimaryContainer,
    inversePrimary = DarkInversePrimary,
    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    secondaryContainer = DarkSecondaryContainer,
    onSecondaryContainer = DarkOnSecondaryContainer,
    tertiary = DarkTertiary,
    onTertiary = DarkOnTertiary,
    tertiaryContainer = DarkTertiaryContainer,
    onTertiaryContainer = DarkOnTertiaryContainer,
    background = DarkSurface,
    onBackground = DarkOnSurface,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    surfaceTint = DarkPrimary,
    inverseSurface = DarkInverseSurface,
    inverseOnSurface = DarkInverseOnSurface,
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    outline = DarkOutline,
    outlineVariant = DarkSurfaceVariant,
    scrim = Color(0xFF000000),
)

internal object ExpressiveExtendedColors {
    val positive = Color(0xFF24C38E)
    val positiveContainer = Color(0xFFBDF4DC)
    val positiveOnContainer = Color(0xFF002116)

    val negative = Color(0xFFFF5370)
    val negativeContainer = Color(0xFFFFDAD7)
    val negativeOnContainer = Color(0xFF410006)

    val neutralOnSurface = ExpressiveOnSurface
    val neutralSurfaceVariant = ExpressiveSurfaceVariant
}

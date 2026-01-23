package com.mall.hrmnew.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val lightAppColor =
    AppLocalColor(
        red = Color(0xFFFF3B30),
        black = Color(0xFF1B1B1B),
        orange = Color(0xFFFF9500),
        yellow = Color(0xFFFFCC00),
        green = Color(0xFF34C759),
        mint = Color(0xFF00C7BE),
        teal = Color(0xFF30B0C7),
        cyan = Color(0xFF32ADE6),
        blue = Color(0xFF007AFF),
        indigo = Color(0xFF5856D6),
        purple = Color(0xFFAF52DE),
        pink = Color(0xFFFF2D55),
        brown = Color(0xFFA2845E),
        gray = Color(0xFF8E8E93),
        gray2 = Color(0xFFAEAEB2),
        gray3 = Color(0xFFC7C7CC),
        gray4 = Color(0xFFD1D1D6),
        gray5 = Color(0xFFE5E5EA),
        gray6 = Color(0xFFF2F2F7),
        pink1 = Color(0xFFA964F8),
        blue2 = Color(0xFF5D48F5)
    )

val darkAppColor =
    AppLocalColor(
        red = Color(0xFFFF453A),
        black = Color(0xFFFFFFFF),
        orange = Color(0xFFFF9F0A),
        yellow = Color(0xFFFFD60A),
        green = Color(0xFF30D158),
        mint = Color(0xFF63E6E2),
        teal = Color(0xFF40CBE0),
        cyan = Color(0xFF64D2FF),
        blue = Color(0xFF0A84FF),
        indigo = Color(0xFF5E5CE6),
        purple = Color(0xFFBF5AF2),
        pink = Color(0xFFFF375F),
        brown = Color(0xFFAC8E68),
        gray = Color(0xFF8E8E93),
        gray2 = Color(0xFF636366),
        gray3 = Color(0xFF48484A),
        gray4 = Color(0xFF3A3A3C),
        gray5 = Color(0xFF2C2C2E),
        gray6 = Color(0xFF1C1C1E),
        pink1 = Color(0xFFA964F8),
        blue2 = Color(0xFF5D48F5)
    )

val LocalAppColor = staticCompositionLocalOf { lightAppColor }

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryLight,
    onPrimaryContainer = PrimaryDark,

    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryLight,
    onSecondaryContainer = SecondaryDark,

    background = Background,
    onBackground = OnBackground,

    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,

    error = Error,
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),

    outline = Outline,
    outlineVariant = OutlineVariant
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryLight,
    onPrimary = Color.White,
    primaryContainer = PrimaryDark,
    onPrimaryContainer = PrimaryLight,

    secondary = SecondaryLight,
    onSecondary = Color.White,
    secondaryContainer = SecondaryDark,
    onSecondaryContainer = SecondaryLight,

    background = Color(0xFF121212),
    onBackground = Color(0xFFE1E1E1),

    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE1E1E1),
    surfaceVariant = Color(0xFF2D2D2D),
    onSurfaceVariant = Color(0xFFBDBDBD),

    error = Error,
    onError = Color.White,
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),

    outline = Color(0xFF424242),
    outlineVariant = Color(0xFF2D2D2D)
)

@Composable
fun AppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = AppTypography,
        content = content
    )
}

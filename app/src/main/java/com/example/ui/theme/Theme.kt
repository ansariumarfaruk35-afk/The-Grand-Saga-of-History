package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = GoldPrimary,
    onPrimary = DarkBg,
    primaryContainer = GoldDark,
    onPrimaryContainer = GoldLight,
    secondary = TerracottaAccent,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF4A2520),
    onSecondaryContainer = Color(0xFFFFDAD4),
    tertiary = DeepLapis,
    background = DarkBg,
    onBackground = DarkText,
    surface = DarkSurface,
    onSurface = DarkText,
    surfaceVariant = DarkCard,
    onSurfaceVariant = DarkTextMuted,
    outline = Color(0xFF4D453C)
)

private val LightColorScheme = lightColorScheme(
    primary = GoldDark,
    onPrimary = Color.White,
    primaryContainer = ParchmentBorder,
    onPrimaryContainer = InkDark,
    secondary = TerracottaAccent,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFDAD4),
    onSecondaryContainer = Color(0xFF410001),
    tertiary = DeepLapis,
    background = ParchmentBg,
    onBackground = InkDark,
    surface = ParchmentSurface,
    onSurface = InkDark,
    surfaceVariant = ParchmentBorder,
    onSurfaceVariant = InkMuted,
    outline = Color(0xFFC7BBA8)
)

@Composable
fun WorldHistoryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

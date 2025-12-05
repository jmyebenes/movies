package dev.jmyp.movies.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Yellow = Color(0xFFFFC107)

// Light Theme
val LightBackground = Color(0xFFF6ECE7)
val LightSurface = Color(0xFFF2DCD2)
val LightPrimary = Color(0xFFE57373)
val LightOnPrimary = Color(0xFFFCEEEA)
val LightSecondary = Color(0xFFFFB74D)
val LightOnSecondary = Color(0xFFFAF3E7)
val LightOutline = Color(0xFFE1BBAF)
val LightText = Color(0xFF402B2B)

// Dark Theme
val DarkBackground = Color(0xFF2A1E1E)
val DarkSurface = Color(0xFF3A2A29)
val DarkPrimary = Color(0xFFFF8A65)
val DarkOnPrimary = Color(0xFF2A1E1E)
val DarkSecondary = Color(0xFFFFB74D)
val DarkOnSecondary = Color(0xFF3A2A29)
val DarkOutline = Color(0xFF5A4442)
val DarkText = Color(0xFFF7E3D8)

val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    secondary = LightSecondary,
    onSecondary = LightOnSecondary,
    background = LightBackground,
    surface = LightSurface,
    onSurface = LightText,
    outline = LightOutline
)

val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    background = DarkBackground,
    surface = DarkSurface,
    onSurface = DarkText,
    outline = DarkOutline
)
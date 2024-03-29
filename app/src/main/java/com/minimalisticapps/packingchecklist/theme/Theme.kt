package com.minimalisticapps.packingchecklist.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = darkColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryVariantColor,
    secondary = SecondaryColorForLight,
    secondaryVariant = SecondaryColorForLight,
    background = BackgroundColorForLight,
    surface = SurfaceColorForDark,
    error = ErrorColor,
    onPrimary = WhiteColor,
    onSecondary = WhiteColor,
    onBackground = BlackColor,
    onSurface = BlackColor,
    onError = ErrorColor
)

private val DarkColorPalette = lightColors(
    background = BackgroundColorForDark,
    surface = SurfaceColorForLight,
    primary = PrimaryColor,
    secondary = SecondaryColorForDark,
    onBackground = OnBackgroundColor,
    onSurface = WhiteColor,
    onPrimary = WhiteColor,
    onSecondary = WhiteColor,
    primaryVariant = PrimaryVariantColor,
    secondaryVariant = SecondaryColorForDark,
    onError = ErrorColor,
    error = ErrorColor,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}

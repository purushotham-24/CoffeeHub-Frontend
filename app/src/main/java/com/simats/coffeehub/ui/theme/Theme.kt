package com.simats.coffeehub.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    background = Background,
    surface = Card,
    primary = Primary,
    secondary = Secondary,
    onPrimary = Foreground,
    onSecondary = Foreground,
    onBackground = Foreground
)

private val DarkColors = darkColorScheme(
    background = DarkBackground,
    surface = DarkCard,
    primary = DarkPrimary,
    secondary = DarkSecondary,
    onPrimary = DarkForeground,
    onSecondary = DarkForeground,
    onBackground = DarkForeground
)

@Composable
fun CoffeeHubTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}

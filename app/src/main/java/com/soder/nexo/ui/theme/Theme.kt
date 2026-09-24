package com.soder.nexo.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF00E5FF),
    onPrimary = Color(0xFF000000),
    primaryContainer = Color(0xFF004D5A),
    onPrimaryContainer = Color(0xFF9CF0FF),
    secondary = Color(0xFF80D8FF),
    onSecondary = Color(0xFF000000),
    background = Color(0xFF101010),
    surface = Color(0xFF181818),
    onBackground = Color(0xFFEEEEEE),
    onSurface = Color(0xFFEEEEEE),
    onSurfaceVariant = Color(0xFFAAAAAA),
    outline = Color(0xFF333333),
    outlineVariant = Color(0xFF262626)
)

@Composable
fun NexoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}

package com.example.schoolproject.ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = CustomColorScheme(
    primary = Color(0xFFFFFFFF),
    secondary = Color(0x99FFFFFF),
    tertiary = Color(0x66FFFFFF),
    disable = Color(0x26FFFFFF),
    separator = Color(0xFFFFFFFF),
    overlay = Color(0x52000000),
    lightGray = Color(0xFF48484A),
    backPrimary = Color(0xFF161618),
    backSecondary = Color(0xFF252528),
    backElevated = Color(0xFF3C3C3F)
)

private val LightColorScheme = CustomColorScheme(
    primary = Color(0xFF992233),
    secondary = Color(0x99000000),
    tertiary = Color(0x4D000000),
    disable = Color(0x26000000),
    separator = Color(0x33000000),
    overlay = Color(0x0F000000),
    lightGray = Color(0xFFD1D1D6),
    backPrimary = Color(0xFFF7F6F2),
    backSecondary = Color(0xFFFFFFFF),
    backElevated = Color(0xFFFFFFFF)
)

@Composable
fun SchoolProjectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val rippleIndicator = rememberRipple()
    CompositionLocalProvider(
        LocalCustomColorScheme provides colorScheme,
        LocalIndication provides rippleIndicator,
        content = content
    )
}
object AppTheme {
    val colorScheme: CustomColorScheme
        @Composable get() = LocalCustomColorScheme.current
}

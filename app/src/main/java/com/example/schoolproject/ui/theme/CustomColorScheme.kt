package com.example.schoolproject.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CustomColorScheme (
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val disable: Color,
    val separator: Color,
    val overlay: Color,
    val lightGray: Color,
    val backPrimary: Color,
    val backSecondary: Color,
    val backElevated: Color
)

val LocalCustomColorScheme = staticCompositionLocalOf {
    CustomColorScheme (
        primary = Color.Unspecified,
        secondary = Color.Unspecified,
        tertiary = Color.Unspecified,
        disable = Color.Unspecified,
        separator = Color.Unspecified,
        overlay = Color.Unspecified,
        lightGray = Color.Unspecified,
        backPrimary = Color.Unspecified,
        backSecondary = Color.Unspecified,
        backElevated = Color.Unspecified,
    )
}
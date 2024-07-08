package com.example.schoolproject.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

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

data class CustomTypography(
    val largeTitle: TextStyle ,
    val title: TextStyle ,
    val button: TextStyle ,
    val body: TextStyle ,
    val subhead: TextStyle
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
        backElevated = Color.Unspecified
    )
}

val LocalCustomTypography = staticCompositionLocalOf {
    CustomTypography (
        largeTitle = TextStyle.Default.copy(
            fontWeight = FontWeight.W500,
            fontSize = 32.sp,
            lineHeight = 38.sp
        ),
        title = TextStyle.Default.copy(
            fontWeight = FontWeight.W500,
            fontSize = 20.sp,
            lineHeight = 32.sp
        ),
        button = TextStyle.Default.copy(
            fontWeight = FontWeight.W500,
            fontSize = 14.sp,
            lineHeight = 24.sp,
        ),
        body = TextStyle.Default.copy(
            fontSize = 16.sp,
            lineHeight = 20.sp,
        ),
        subhead = TextStyle.Default.copy(
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    )
}
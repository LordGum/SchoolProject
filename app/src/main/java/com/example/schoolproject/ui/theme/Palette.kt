package com.example.schoolproject.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun TextStyles() {
    Column(
        modifier = Modifier
            .height(150.dp)
            .background(Color.White)
            .padding(20.dp)
    ) {
        Text(text = "Large title - 32/48", style = AppTheme.typography.largeTitle)
        Text(text = "Title - 20/32", style = AppTheme.typography.title)
        Text(text = "Button - 14/24", style = AppTheme.typography.button)
        Text(text = "Body - 16/20", style = AppTheme.typography.body)
        Text(text = "Subhead - 14/20", style = AppTheme.typography.subhead)
    }
}

@Preview
@Composable
private fun ProjectThemeLight() {
    SchoolProjectTheme(
        darkTheme = false
    ){
        Palette(true)
    }
}

@Preview
@Composable
private fun ProjectThemeDark() {
    SchoolProjectTheme(
        darkTheme = true
    ){
        Palette(false)
    }
}

@Composable
fun Palette(isLight: Boolean) {
    val theme = if (isLight) "Light" else "Dark"
    Column(modifier = Modifier.fillMaxWidth()) {
        Row {
            Item(
                background = AppTheme.colorScheme.separator,
                textColor = Color.Black,
                text = "Support [$theme] / Separator"
            )
            Item(
                background = AppTheme.colorScheme.overlay,
                textColor = Color.Black,
                text = "Support [$theme] / Overlay"
            )
        }
        Row {
            Item(
                background = AppTheme.colorScheme.primary,
                textColor = if (isLight) Color.White else Color.Black,
                text = "Label [$theme] / Primary"
            )
            Item(
                background = AppTheme.colorScheme.secondary,
                textColor = if (isLight) Color.White else Color.Black,
                text = "Label [$theme] / Secondary"
            )
            Item(
                background = AppTheme.colorScheme.tertiary,
                textColor = Color.Unspecified,
                text = "Label [$theme] / Tertiary"
            )
            Item(
                background = AppTheme.colorScheme.disable,
                textColor = Color.Unspecified,
                text = "Label [$theme] / Disable"
            )
        }
        Row {
            Item(
                background = Red,
                textColor = Color.White,
                text = "Color [$theme] / Red"
            )
            Item(
                background = Green,
                textColor = Color.White,
                text = "Color [$theme] / Green"
            )
            Item(
                background = Blue,
                textColor = Color.White,
                text = "Color [$theme] / Blue"
            )
            Item(
                background = Gray,
                textColor = Color.White,
                text = "Color [$theme] / Gray"
            )
            Item(
                background = AppTheme.colorScheme.lightGray,
                textColor = if (isLight) Color.Black else Color.White,
                text = "Color [$theme] / Gray Light"
            )
        }
        Row {
            Item(
                background = AppTheme.colorScheme.backPrimary,
                textColor = if (isLight) Color.Black else Color.White,
                text = "Back [$theme] / Primary"
            )
            Item(
                background = AppTheme.colorScheme.backSecondary,
                textColor = if (isLight) Color.Black else Color.White,
                text = "Back [$theme] / Secondary"
            )
            Item(
                background = AppTheme.colorScheme.backElevated,
                textColor = if (isLight) Color.Black else Color.White,
                text = "Back [$theme] / Elevated"
            )
        }
    }
}

@Composable
fun Item(
    background: Color,
    textColor: Color,
    text: String
) {
    Box(
        modifier = Modifier
            .background(background)
            .padding(8.dp)
            .height(50.dp)
            .width(60.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Text(text = text, color = textColor)
    }
}
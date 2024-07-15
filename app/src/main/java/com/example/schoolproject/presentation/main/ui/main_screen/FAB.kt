package com.example.schoolproject.presentation.main.ui.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.schoolproject.ui.theme.Blue

@Composable
fun FAB(
    isDark: Boolean,
    onAddButtonClick: () -> Unit,
    onThemeChange: (Boolean) -> Unit
) {
    Column {
        DropSettingsMenu(
            isDark,
            onThemeChange
        )
        FloatingActionButton(
            onClick = { onAddButtonClick() },
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(),
            containerColor = Blue,
            modifier = Modifier
                .size(60.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                tint = Color.White,
                contentDescription = null
            )
        }
    }

}
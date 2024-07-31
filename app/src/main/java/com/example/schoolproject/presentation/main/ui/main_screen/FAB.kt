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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import com.example.schoolproject.R
import com.example.schoolproject.ui.theme.Blue

@Composable
fun FAB(
    isDark: Boolean,
    onAddButtonClick: () -> Unit,
    onThemeChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier.semantics {
            isTraversalGroup = true
        }
    ) {
        val context = LocalContext.current
        DropSettingsMenu(
            isDark,
            onThemeChange,
            Modifier.semantics {
                contentDescription = context.getString(R.string.open_theme_buttons)
                role = Role.Button
                traversalIndex = 2f
            }
        )
        FloatingActionButton(
            onClick = { onAddButtonClick() },
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(),
            containerColor = Blue,
            modifier = Modifier
                .size(60.dp)
                .semantics {
                    contentDescription = context.getString(R.string.add_new_task)
                    role = Role.Button
                    traversalIndex = 1f
                }
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                tint = Color.White,
                contentDescription = null
            )
        }
    }

}
package com.example.schoolproject.presentation.main.ui.main_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.schoolproject.R
import com.example.schoolproject.ui.theme.AppTheme
import com.example.schoolproject.ui.theme.Blue
import com.example.schoolproject.ui.theme.Gray

@Composable
fun DropSettingsMenu(
    isDark: Boolean,
    onThemeChange:(Boolean) -> Unit,
    modifier: Modifier
) {
    val currentTheme = isSystemInDarkTheme()
    val isClicked = remember { mutableStateOf(false) }
    val heightSize = animateDpAsState(
        targetValue = if (isClicked.value) 145.dp else 35.dp,
        label = "heightSize"
    )

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(start = 40.dp)
    ) {
        FloatingActionButton(
            onClick = {
                isClicked.value = !isClicked.value
            },
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(),
            containerColor = Gray,
            modifier = modifier.size(35.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Settings,
                tint = Color.White,
                contentDescription = null
            )
        }

        AnimatedVisibility(
            visible = isClicked.value,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(
                modifier = Modifier
                    .height(heightSize.value)
                    .padding(start = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                CustomButton(
                    onButtonClick = {
                        isClicked.value = false
                        if (isDark) onThemeChange(false)
                    },
                    id = R.drawable.ic_light_theme
                )
                Spacer(modifier = Modifier.height(10.dp))
                CustomButton(
                    onButtonClick = {
                        isClicked.value = false
                        if (!isDark) onThemeChange(true)
                    },
                    id = R.drawable.ic_dark_theme
                )
                Spacer(modifier = Modifier.height(10.dp))
                CustomButton(
                    onButtonClick = {
                        isClicked.value = false
                        if (currentTheme) onThemeChange(true)
                        else onThemeChange(false)
                    },
                    id = R.drawable.ic_settings
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun CustomButton(
    onButtonClick: () -> Unit,
    id: Int
) {
    Surface(
        shape = CircleShape,
        border = BorderStroke(1.dp, AppTheme.colorScheme.separator)
    ) {
        IconButton(
            onClick = onButtonClick,
            modifier = Modifier
                .size(30.dp)
                .padding(5.dp)
                .background(color = AppTheme.colorScheme.backSecondary)
        ) {
            Icon(
                painter = painterResource(id),
                tint = Blue,
                contentDescription = null
            )
        }
    }
}
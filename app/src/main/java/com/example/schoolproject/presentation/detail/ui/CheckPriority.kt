package com.example.schoolproject.presentation.detail.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.schoolproject.R
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.ui.theme.AppTheme
import com.example.schoolproject.ui.theme.Green
import com.example.schoolproject.ui.theme.Red
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun PriorityItem(
    priority: TodoItem.Priority,
    onPriorityItem: () -> Unit,
) {
    val enabled = rememberSaveable { mutableStateOf(false) }
    val highPriorityColor by animateColorAsState(
        targetValue = if (enabled.value) Red.copy(alpha = 0.20f) else AppTheme.colorScheme.backPrimary,
        label = "delete_button_color_animation"
    )
    val lowPriorityColor by animateColorAsState(
        targetValue = if (enabled.value) Green.copy(alpha = 0.20f) else AppTheme.colorScheme.backPrimary,
        label = "delete_button_color_animation"
    )

    Surface(
        modifier = Modifier
            .clickable(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        if (priority != TodoItem.Priority.NORMAL) {
                            enabled.value = true
                        }
                        delay(200)
                        onPriorityItem()
                    }
                },
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            )
            .background(color = AppTheme.colorScheme.backPrimary)
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 1.dp, color = AppTheme.colorScheme.separator)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (priority == TodoItem.Priority.HIGH) highPriorityColor
                    else lowPriorityColor
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(getPriority(priority)),
                style = AppTheme.typography.button,
                color = AppTheme.colorScheme.primary
            )
        }
    }
}

fun getPriority(priority: TodoItem.Priority): Int {
    val options = mapOf(
        TodoItem.Priority.LOW to (R.string.low_priority),
        TodoItem.Priority.NORMAL to (R.string.lnormal_priority),
        TodoItem.Priority.HIGH to (R.string.high_priority),
    )

    return options[priority] ?: throw RuntimeException("problem in fun \"getPriority\"")
}
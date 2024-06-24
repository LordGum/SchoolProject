package com.example.schoolproject.ui.theme.detail_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.schoolproject.R
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.ui.theme.AppTheme
import com.example.schoolproject.ui.theme.Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriorityMenu(
    item: TodoItem
) {
    val options = mapOf(
        TodoItem.Priority.LOW to stringResource(R.string.low_priority),
        TodoItem.Priority.NORMAL to stringResource(R.string.lnormal_priority),
        TodoItem.Priority.HIGH to stringResource(R.string.high_priority),
    )

    val expanded = rememberSaveable { mutableStateOf(false) }
    val currentPriority = remember { mutableStateOf(item.priority) }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = it }
    ) {
        Text(
            text = options[currentPriority.value] ?: stringResource(R.string.lnormal_priority),
            modifier = Modifier
                .menuAnchor()
                .width(164.dp),
            color = AppTheme.colorScheme.tertiary
        )
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier.background(AppTheme.colorScheme.backSecondary)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.value, style = MaterialTheme.typography.bodyLarge) },
                    onClick = {
                        currentPriority.value = option.key
                        expanded.value = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    colors = MenuDefaults.itemColors(
                        textColor = if (option.value != options[TodoItem.Priority.HIGH]) AppTheme.colorScheme.primary
                        else Red
                    ),
                )
            }
        }
    }
}


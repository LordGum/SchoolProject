package com.example.schoolproject.presentation.detail

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.schoolproject.R
import com.example.schoolproject.ui.theme.AppTheme
import com.example.schoolproject.ui.theme.Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriorityMenu() {
    val options = listOf(
        stringResource(R.string.low_priority),
        stringResource(R.string.lnormal_priority),
        stringResource(R.string.high_priority)
    )
    val expanded = remember { mutableStateOf(false) }
    val text = remember { mutableStateOf(options[1]) }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = it }
    ) {
        Text(
            text = text.value,
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
                    text = { Text(option, style = MaterialTheme.typography.bodyLarge) },
                    onClick = {
                        text.value = option
                        expanded.value = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    colors = MenuDefaults.itemColors(
                        textColor = if (option != options[2]) AppTheme.colorScheme.primary
                                    else Red
                    ),
                )
            }
        }
    }
}
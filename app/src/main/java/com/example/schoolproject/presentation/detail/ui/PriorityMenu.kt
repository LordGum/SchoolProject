package com.example.schoolproject.presentation.detail.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriorityMenu(
    item: TodoItem,
    onItemChange: (TodoItem) -> Unit
) {
    val openBottomSheet = rememberSaveable { mutableStateOf(false) }
    val skipPartiallyExpanded = rememberSaveable { mutableStateOf(false) }
    val bottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded.value)

    TextButton(
        modifier = Modifier.padding(start = 5.dp),
        colors = ButtonDefaults.textButtonColors(
            contentColor = AppTheme.colorScheme.tertiary
        ),
        onClick = { openBottomSheet.value = true }
    ) {
        Text(text = stringResource(id =getPriority(item.priority)))
    }

    if (openBottomSheet.value) {
        ModalBottomSheet(
            containerColor = AppTheme.colorScheme.backPrimary,
            onDismissRequest = { openBottomSheet.value = false },
            sheetState = bottomSheetState,
        ) {
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PriorityItem(priority = TodoItem.Priority.LOW) {
                    openBottomSheet.value = false
                    onItemChange(item.copy(priority = TodoItem.Priority.LOW))
                }
                PriorityItem(priority = TodoItem.Priority.NORMAL) {
                    openBottomSheet.value = false
                    onItemChange(item.copy(priority = TodoItem.Priority.NORMAL))
                }
                PriorityItem(priority = TodoItem.Priority.HIGH) {
                    openBottomSheet.value = false
                    onItemChange(item.copy(priority = TodoItem.Priority.HIGH))
                }


            }
        }
    }
}


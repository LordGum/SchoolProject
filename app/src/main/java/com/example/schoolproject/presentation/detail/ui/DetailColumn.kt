package com.example.schoolproject.presentation.detail.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schoolproject.R
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.ui.theme.AppTheme
import com.example.schoolproject.ui.theme.Blue
import com.example.schoolproject.ui.theme.Gray
import com.example.schoolproject.ui.theme.Red
import okhttp3.internal.format
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DetailColumn(
    item: TodoItem,
    onItemChange: (TodoItem) -> Unit,
    onDeleteIconClickListener: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colorScheme.backPrimary)
            .padding(0.dp, 16.dp, 12.dp, 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        PriorityUI(
            item = item,
            onItemChange = onItemChange
        )
        DeadlineUI(
            item = item,
            onItemChange = onItemChange
        )
        DeleteButtonUI(
            canDelete = item.text.isNotBlank(),
            onDeleteIconClickListener = onDeleteIconClickListener
        )
    }
}

@Composable
private fun PriorityUI(
    item: TodoItem,
    onItemChange: (TodoItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(92.dp)
            .padding(start = 0.dp, 8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(R.string.important),
            color = AppTheme.colorScheme.primary,
            style = AppTheme.typography.body
        )
        PriorityMenu(
            item = item,
            onItemChange = onItemChange
        )
        Spacer(modifier = Modifier.height(4.dp))
        Canvas(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxSize()
                .height(1.dp)
        ) {
            itemLine(Gray)
        }
    }
}

private fun DrawScope.itemLine(
    color: Color
) {
    drawLine(
        color = color.copy(alpha = 0.5f),
        start = Offset(0f, size.height),
        end = Offset(size.width, size.height),
        strokeWidth = 1.dp.toPx(),
    )
}

@Composable
private fun DeadlineUI(
    item: TodoItem,
    onItemChange: (TodoItem) -> Unit
) {
    val checkState = rememberSaveable { mutableStateOf(item.deadline != null) }
    val openDialogState = rememberSaveable { mutableStateOf(false) }
    val currentItem = rememberSaveable { mutableStateOf(item) }
    val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(start = 16.dp, 8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .clickable(
                        onClickLabel = if (currentItem.value.deadline == null) {
                            stringResource(R.string.add_deadline)
                        } else stringResource(R.string.change_deadline)
                    ) {
                        openDialogState.value = true
                    }
                    .semantics(mergeDescendants = true) {
                        contentDescription = if (currentItem.value.deadline != null) {
                            format(
                                context.getString(R.string.deadline),
                                formatter.format(
                                    currentItem.value.deadline
                                        ?: throw RuntimeException("date is null")
                                )
                            )
                        } else {
                            context.getString(R.string.no_deadline)
                        }
                    },
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = stringResource(R.string.deadline),
                    color = AppTheme.colorScheme.primary,
                    style = AppTheme.typography.body,
                    modifier = Modifier.semantics { contentDescription = "" }
                )
                Spacer(modifier = Modifier.height(4.dp))
                if (currentItem.value.deadline != null) {
                    Text(
                        text = formatter.format(
                            currentItem.value.deadline ?: throw RuntimeException("date is null")
                        ),
                        color = Blue,
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default
                    )
                }
            }
            CustomSwitch(
                checkState = checkState.value,
                onOpenDialogChange = { openDialogState.value = it },
                onCheckStateChange = {
                    checkState.value = it
                    if (!it) {
                        onItemChange(item.copy(deadline = null))
                        currentItem.value = item.copy(deadline = null)
                    }
                }
            )
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .height(1.dp)
        ) {
            itemLine(Gray)
        }
    }

    if (openDialogState.value) {
        CalendarMenu(
            onOpenDialogChange = { openDialogState.value = it },
            calendarStateChange = {
                checkState.value = it
                if (!it) {
                    onItemChange(item.copy(deadline = null))
                    currentItem.value = item.copy(deadline = null)
                }
            },
            item = item,
            onItemChange = {
                currentItem.value = it
                onItemChange(it)
            }
        )
    }
}

@Composable
fun CustomSwitch(
    checkState: Boolean,
    onOpenDialogChange: (Boolean) -> Unit,
    onCheckStateChange: (Boolean) -> Unit
) {
    val context = LocalContext.current
    Switch(
        modifier = Modifier.semantics {
            contentDescription = if (checkState) {
                context.getString(R.string.delete_deadline)
            } else context.getString(R.string.add_deadline)
        },
        checked = checkState,
        onCheckedChange = {
            onOpenDialogChange(it)
            onCheckStateChange(it)
        },
        thumbContent = {
            if (checkState) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                )
            }
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = AppTheme.colorScheme.backSecondary,
            checkedTrackColor = Blue,
            checkedIconColor = AppTheme.colorScheme.primary,
            uncheckedThumbColor = Gray,
            uncheckedTrackColor = AppTheme.colorScheme.separator,
            uncheckedBorderColor = Color.Transparent
        )
    )
}

// Здесь contentDescription = null и Modifier.semantics { contentDescription = "" }
// чтобы не было повторения информациии

@Composable
fun DeleteButtonUI(
    canDelete: Boolean,
    onDeleteIconClickListener: () -> Unit
) {
    val context = LocalContext.current
    TextButton(
        modifier = Modifier.semantics(mergeDescendants = true) {
            contentDescription = if (canDelete) context.getString(R.string.delete_task)
            else context.getString(R.string.refuse_delete_empty_task)
        },
        onClick = onDeleteIconClickListener,
        enabled = canDelete
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = if (canDelete) Red else AppTheme.colorScheme.disable,
            modifier = Modifier.size(24.dp)
        )
        Text(
            modifier = Modifier.clearAndSetSemantics { },
            text = stringResource(R.string.delete),
            color = if (canDelete) Red else AppTheme.colorScheme.disable,
            style = AppTheme.typography.subhead
        )
    }
}
package com.example.schoolproject.presentation.detail

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schoolproject.R
import com.example.schoolproject.ui.theme.AppTheme
import com.example.schoolproject.ui.theme.Blue
import com.example.schoolproject.ui.theme.Gray
import com.example.schoolproject.ui.theme.Red

@Composable
fun DetailColumn(
    canDelete: Boolean,
    onDeleteIconClickListener: () -> Unit
) {
    val checkState = rememberSaveable{ mutableStateOf(false) }
    val openDialogState = rememberSaveable{ mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .background(AppTheme.colorScheme.backPrimary)
        .padding(12.dp, 16.dp, 12.dp, 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Item1()
        Item2(
            checkState = checkState.value,
            onOpenDialogChange = {
                openDialogState.value = it
            },
            onCheckStateChange = {
                checkState.value = it
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Item3(canDelete = canDelete, onDeleteIconClickListener = onDeleteIconClickListener)
    }

    CalendarMenu(
        openDialog = openDialogState.value,
        onOpenDialogChange = {
            openDialogState.value = it
        },
        calendarStateChange = {
            checkState.value = it
        }
    )
}

@Composable
private fun Item1() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = stringResource(R.string.important),
            color = AppTheme.colorScheme.primary,
            fontSize = 16.sp,
            fontFamily = FontFamily.Default
        )
        Spacer(modifier = Modifier.height(4.dp))
        PriorityMenu()
        Spacer(modifier = Modifier.height(8.dp))
        Canvas(modifier = Modifier
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
private fun Item2(
    checkState: Boolean,
    onOpenDialogChange: (Boolean) -> Unit,
    onCheckStateChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.deadline),
                color = AppTheme.colorScheme.primary,
                fontSize = 16.sp,
                fontFamily = FontFamily.Default
            )
            CustomSwitch(
                checkState = checkState,
                onOpenDialogChange = onOpenDialogChange,
                onCheckStateChange = onCheckStateChange
            )
        }

        Canvas(modifier = Modifier
            .fillMaxSize()
            .height(1.dp)
        ) {
            itemLine(Gray)
        }
    }
}

@Composable
fun CustomSwitch(
    checkState: Boolean,
    onOpenDialogChange: (Boolean) -> Unit,
    onCheckStateChange: (Boolean) -> Unit
) {
    Switch(
        modifier = Modifier.semantics { contentDescription = "Add deadline?" },
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

@Composable
fun Item3(
    canDelete: Boolean,
    onDeleteIconClickListener: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .clickable {
                if (canDelete) onDeleteIconClickListener()
            },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(R.string.delete_button_desc),
            tint = if (canDelete) Red else AppTheme.colorScheme.disable,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = stringResource(R.string.delete),
            color = if (canDelete) Red else AppTheme.colorScheme.disable,
            fontSize = 16.sp,
            fontFamily = FontFamily.Default,
        )
    }
}
package com.example.schoolproject.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schoolproject.R
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.ui.theme.AppTheme
import com.example.schoolproject.ui.theme.Blue
import com.example.schoolproject.ui.theme.Red
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarMenu(
    item: TodoItem,
    onItemChange: (TodoItem) -> Unit,
    openDialog: Boolean,
    onOpenDialogChange: (Boolean) -> Unit,
    calendarStateChange: (Boolean) -> Unit,
) {
    val snackState = remember { SnackbarHostState() }
    val error = rememberSaveable { mutableStateOf(false) }
    SnackbarHost(hostState = snackState, Modifier)

    if (openDialog) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = {
                onOpenDialogChange(false)
            },
            confirmButton = {
                TextButton(onClick = {
                    val currentDate = datePickerState.selectedDateMillis ?: Date().time
                    if (currentDate < Date().time) {
                        error.value = true
                    } else {
                        error.value = false
                        onItemChange(item.copy(deadline = Date(currentDate)))
                        onOpenDialogChange(false)
                        calendarStateChange(true)
                    }
                }) {
                    Text(
                        stringResource(R.string.ok_calendar),
                        color = Blue
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onOpenDialogChange(false)
                    calendarStateChange(false)
                }) {
                    Text(
                        stringResource(R.string.no_calendar),
                        color = Blue
                    ) }
            },
            colors = DatePickerDefaults.colors(containerColor =  AppTheme.colorScheme.backSecondary)
        ) {
            val dateFormatter = remember { DatePickerFormatter() }
            DatePicker(
                modifier = Modifier.background( AppTheme.colorScheme.backSecondary ),
                state = datePickerState,
                title = {
                    Text(
                        text = if (error.value) stringResource(R.string.choose_date_cancel)
                                else stringResource(R.string.chose_date),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 16.dp, start = 24.dp)
                )},
                headline = {
                    DatePickerDefaults.DatePickerHeadline(
                        datePickerState,
                        dateFormatter,
                        modifier = Modifier.padding(top = 8.dp, start = 24.dp, bottom = 24.dp)
                    )
                },
                showModeToggle = false,
                colors = DatePickerDefaults.colors(
                    containerColor = AppTheme.colorScheme.backSecondary,
                    currentYearContentColor = Color.White,
                    selectedYearContainerColor = Blue,
                    todayContentColor = Blue,
                    todayDateBorderColor = Blue,
                    disabledDayContentColor = AppTheme.colorScheme.primary,
                    selectedDayContentColor = Color.White,
                    selectedDayContainerColor = if (error.value) Red else Blue,
                    dayContentColor = AppTheme.colorScheme.primary,
                    weekdayContentColor = AppTheme.colorScheme.tertiary,
                    titleContentColor = if (error.value) Red else AppTheme.colorScheme.primary,
                    headlineContentColor = AppTheme.colorScheme.primary,
                    yearContentColor = AppTheme.colorScheme.tertiary
                )
            )
        }
    }
}
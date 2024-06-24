package com.example.schoolproject.ui.theme.detail_screen

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schoolproject.R
import com.example.schoolproject.ui.theme.AppTheme
import com.example.schoolproject.ui.theme.Blue
import com.example.schoolproject.ui.theme.Red
import java.util.Date

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarMenu() {
    val snackState = remember { SnackbarHostState() }
    SnackbarHost(hostState = snackState)
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = {},
            confirmButton = {
                TextButton(onClick = {}) {
                    Text(
                        stringResource(R.string.ok_calendar),
                        color = Blue
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {}) {
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
                        text = stringResource(R.string.chose_date),
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
                    selectedDayContainerColor =  Blue,
                    dayContentColor = AppTheme.colorScheme.primary,
                    weekdayContentColor = AppTheme.colorScheme.tertiary,
                    titleContentColor = AppTheme.colorScheme.primary,
                    headlineContentColor = AppTheme.colorScheme.primary,
                    yearContentColor = AppTheme.colorScheme.tertiary
                )
            )
        }

}
package com.example.schoolproject.ui.theme.detail_screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.ui.theme.SchoolProjectTheme
import java.util.Date

@Preview
@Composable
private fun ProjectThemeLight() {
    SchoolProjectTheme(
        darkTheme = false
    ){
        val todoItem = (TodoItem(1, "Buy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceries", TodoItem.Priority.LOW, isCompleted =  false, Date(), null))
        DetailScreenContent(todoItem, isCalendar = false)
    }
}

@Preview
@Composable
private fun ProjectThemeDark() {
    SchoolProjectTheme(
        darkTheme = true
    ){
        val todoItem = (TodoItem(1, "Buy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceries", TodoItem.Priority.LOW, isCompleted =  false, Date(), null, deadline = Date()))
        DetailScreenContent(todoItem, isCalendar = true)
    }
}
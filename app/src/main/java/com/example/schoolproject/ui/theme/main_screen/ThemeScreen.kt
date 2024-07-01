package com.example.schoolproject.ui.theme.main_screen

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
        val list = ArrayList<TodoItem>()
        list.add(TodoItem(0, "Attend team meeting 2", priority = TodoItem.Priority.NORMAL, createdDate = Date(), isCompleted = true))
        list.add(TodoItem(1, "Buy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceries", TodoItem.Priority.LOW, isCompleted =  false, Date(), null))
        list.add(TodoItem(2, "Complete project report", TodoItem.Priority.HIGH, createdDate = Date(), isCompleted =false))
        list.add(TodoItem(3, "Pay utility bills", TodoItem.Priority.NORMAL, createdDate = Date(), isCompleted =false, deadline = Date()))
        list.add(TodoItem(4, "Book doctor appointment", TodoItem.Priority.HIGH, createdDate = Date(), isCompleted =true))
        list.add(TodoItem(5, "Clean the house", TodoItem.Priority.LOW, createdDate = Date(), isCompleted = true, deadline = Date()))
        list.add(TodoItem(6, "Attend team meeting", TodoItem.Priority.NORMAL, createdDate = Date(), isCompleted =false, deadline = Date()))
        list.add(TodoItem(7, "Renew car insurance Renew car insurance Renew car insurance Renew car insurance Renew car insurance Renew car insurance Renew car insurance Renew car insurance Renew car insurance", TodoItem.Priority.HIGH, false, createdDate = Date()))
        list.add(TodoItem(8, "Read a book", TodoItem.Priority.LOW, isCompleted = true, createdDate = Date()))
        list.add(TodoItem(9, "Exercise", TodoItem.Priority.NORMAL, isCompleted = false, createdDate = Date()))
        list.add(TodoItem(10, "Plan vacation", TodoItem.Priority.LOW, createdDate = Date(), isCompleted = false))
        list.add(TodoItem(11, "Prepare for presentation", TodoItem.Priority.HIGH, createdDate = Date(),isCompleted = false))
        list.add(TodoItem(12, "Organize workspace", TodoItem.Priority.NORMAL, createdDate = Date(), isCompleted =false))
        list.add(TodoItem(13, "Buy groceries 2", TodoItem.Priority.HIGH,  createdDate = Date(), isCompleted = false))
        MainScreenTheme(list = list, countDone = 6, visibilityState = false)
    }
}

@Preview
@Composable
private fun ProjectThemeDark() {
    SchoolProjectTheme(
        darkTheme = true
    ){
        val list = ArrayList<TodoItem>()
        list.add(TodoItem(0, "Attend team meeting 2", priority = TodoItem.Priority.NORMAL, createdDate = Date(), isCompleted = true, deadline = Date()))
        list.add(TodoItem(1, "Buy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceriesBuy groceries", TodoItem.Priority.LOW, isCompleted =  false, Date(), null))
        list.add(TodoItem(2, "Complete project report", TodoItem.Priority.HIGH, createdDate = Date(), isCompleted =false))
        list.add(TodoItem(3, "Pay utility bills", TodoItem.Priority.NORMAL, createdDate = Date(), isCompleted =false, deadline = Date()))
        list.add(TodoItem(4, "Book doctor appointment", TodoItem.Priority.HIGH, createdDate = Date(), isCompleted =true))
        list.add(TodoItem(5, "Clean the house", TodoItem.Priority.LOW, createdDate = Date(), isCompleted = true))
        list.add(TodoItem(6, "Attend team meeting", TodoItem.Priority.NORMAL, createdDate = Date(), isCompleted =false))
        list.add(TodoItem(7, "Renew car insurance Renew car insurance Renew car insurance Renew car insurance Renew car insurance Renew car insurance Renew car insurance Renew car insurance Renew car insurance", TodoItem.Priority.HIGH, false, createdDate = Date()))
        list.add(TodoItem(8, "Read a book", TodoItem.Priority.LOW, isCompleted = true, createdDate = Date()))
        list.add(TodoItem(9, "Exercise", TodoItem.Priority.NORMAL, isCompleted = false, createdDate = Date()))
        list.add(TodoItem(10, "Plan vacation", TodoItem.Priority.LOW, createdDate = Date(), isCompleted = false))
        list.add(TodoItem(11, "Prepare for presentation", TodoItem.Priority.HIGH, createdDate = Date(),isCompleted = false))
        list.add(TodoItem(12, "Organize workspace", TodoItem.Priority.NORMAL, createdDate = Date(), isCompleted =false))
        list.add(TodoItem(13, "Buy groceries 2", TodoItem.Priority.HIGH,  createdDate = Date(), isCompleted = false))
        MainScreenTheme(list = list, countDone = 6, visibilityState = true)
    }
}
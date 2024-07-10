package com.example.schoolproject.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.presentation.main.ui.InitialScreen
import com.example.schoolproject.presentation.main.ui.NoInternetScreen
import com.example.schoolproject.presentation.main.ui.main_screen.MainScreenContent
import com.example.schoolproject.presentation.ui_elements.LoadingScreen
import kotlinx.coroutines.Deferred

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    isConnectInternet: Boolean,
    onTodoItemClick: (TodoItem) -> Unit,
    onAddButtonClick: () -> Unit,
    onDeleteClick: (String) -> Unit,
    onDoneClick: (TodoItem) -> Unit,
    onRefreshTodoList: () -> Deferred<Unit>
) {
    val screenState = viewModel.screenState.collectAsState(MainScreenState.Loading)
    var currentState = screenState.value
//    var currentState = if (isConnectInternet) screenState.value else MainScreenState.NoInternet
    if (currentState is MainScreenState.TodoList) {
        if (currentState.todoList.isEmpty()) currentState = MainScreenState.Initial
    }
    val visibilityState = remember { mutableStateOf(true) }

    when (currentState) {
        is MainScreenState.TodoList -> {
            MainScreenContent(
                list = currentState.todoList,
                onTodoItemClick = onTodoItemClick,
                onAddButtonClick = onAddButtonClick,
                onDoneClick = onDoneClick,
                onDeleteClick = onDeleteClick,
                countDone = currentState.count,
                onVisibilityIconClick = { visibilityState.value = !visibilityState.value },
                visibilityState = visibilityState.value,
                onRefreshTodoList = { onRefreshTodoList() }
            )
        }
        is MainScreenState.Loading -> {
            LoadingScreen()
        }
        is MainScreenState.Initial -> {
            InitialScreen(onAddButtonClick = onAddButtonClick)
        }
        is MainScreenState.NoInternet -> {
            NoInternetScreen()
        }
    }
}


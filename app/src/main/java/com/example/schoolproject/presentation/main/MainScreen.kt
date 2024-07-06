package com.example.schoolproject.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.presentation.main.ui.InitialScreen
import com.example.schoolproject.presentation.main.ui.main_screen.MainScreenContent
import com.example.schoolproject.presentation.main.ui.NoInternetScreen
import com.example.schoolproject.presentation.ui_elements.LoadingScreen

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    isConnectInternet: Boolean,
    onTodoItemClick: (TodoItem) -> Unit,
    onAddButtonClick: () -> Unit
) {
    val screenState = viewModel.screenState.collectAsState(MainScreenState.Loading)
    var currentState = if (isConnectInternet) screenState.value else MainScreenState.NoInternet
    if (currentState is MainScreenState.TodoList) {
        if (currentState.todoList.isEmpty()) currentState = MainScreenState.Initial
    }
    val visibilityState = remember { mutableStateOf(true) }

    when (currentState) {
        is MainScreenState.TodoList -> {
            MainScreenContent(
                list = currentState.todoList,
                onTodoItemClickListener = onTodoItemClick,
                onAddButtonClick = onAddButtonClick,
                viewModel = viewModel,
                countDone = currentState.count,
                onVisibilityIconClick = {
                    visibilityState.value = !visibilityState.value
                },
                visibilityState.value
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


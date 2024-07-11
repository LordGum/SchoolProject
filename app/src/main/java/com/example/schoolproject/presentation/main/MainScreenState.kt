package com.example.schoolproject.presentation.main

import com.example.schoolproject.domain.entities.ErrorState
import com.example.schoolproject.domain.entities.TodoItem

sealed class MainScreenState {
    data object Initial: MainScreenState()

    data object Loading: MainScreenState()

    data class TodoList(
        val todoList: List<TodoItem>,
        var count: Int,
        val errorState: ErrorState?
    ): MainScreenState()
}
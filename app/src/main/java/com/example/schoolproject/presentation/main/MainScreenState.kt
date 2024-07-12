package com.example.schoolproject.presentation.main

import com.example.schoolproject.domain.entities.ErrorState
import com.example.schoolproject.domain.entities.TodoItem

sealed class MainScreenState {
    data object Initial: MainScreenState()

    data object Loading: MainScreenState()

    data class TodoList(
        val todoList: List<TodoItem> = emptyList(),
        var count: Int = 0,
        val errorState: ErrorState? = null
    ): MainScreenState()
}
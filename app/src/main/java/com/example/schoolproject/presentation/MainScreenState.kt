package com.example.schoolproject.presentation

import com.example.schoolproject.domain.entities.TodoItem

sealed class MainScreenState {
    data object Initial: MainScreenState()

    data class TodoList(
        val todoList: List<TodoItem>
    ): MainScreenState()
}
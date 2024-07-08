package com.example.schoolproject.presentation.detail

import com.example.schoolproject.domain.entities.TodoItem


sealed class DetailScreenState {
    data object LoadingState: DetailScreenState()

    data object ErrorState: DetailScreenState()

    data class AddTodoItemState(val item: TodoItem): DetailScreenState()

    data class RefactorTodoItemState(val item: TodoItem): DetailScreenState()
}
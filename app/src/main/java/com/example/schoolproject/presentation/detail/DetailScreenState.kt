package com.example.schoolproject.presentation.detail

import com.example.schoolproject.domain.entities.TodoItem


sealed class DetailScreenState {
    data object LoadingState: DetailScreenState()

    data object ErrorState: DetailScreenState()

    data class TodoItemState(val item: TodoItem): DetailScreenState()
}
package com.example.schoolproject.domain.usecases

import com.example.schoolproject.domain.TodoItemsRepository

class GetTodoListUseCase(
    private val repository: TodoItemsRepository
) {
    operator fun invoke() = repository.todoList
}
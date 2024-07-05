package com.example.schoolproject.domain.usecases.database

import com.example.schoolproject.domain.TodoItemsRepository

class DeleteTodoItemUseCase (
    private val repository: TodoItemsRepository
) {
    suspend operator fun invoke(id: String) = repository.deleteTodoItem(id)
}
package com.example.schoolproject.domain.usecases

import com.example.schoolproject.domain.TodoItemsRepository

class DeleteTodoItemUseCase (
    private val repository: TodoItemsRepository
) {
    suspend operator fun invoke(id: Int) = repository.deleteTodoItem(id)
}
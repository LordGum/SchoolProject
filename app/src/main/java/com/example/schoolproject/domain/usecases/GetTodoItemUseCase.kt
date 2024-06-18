package com.example.schoolproject.domain.usecases

import com.example.schoolproject.domain.TodoItemsRepository

class GetTodoItemUseCase (
    private val repository: TodoItemsRepository
) {
    suspend operator fun invoke(id: String) = repository.getTodoItem(id)
}
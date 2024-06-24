package com.example.schoolproject.domain.usecases

import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.domain.TodoItemsRepository

class AddTodoItemUseCase (
    private val repository: TodoItemsRepository
) {
    suspend operator fun invoke(item: TodoItem) = repository.addTodoItem(item)
}
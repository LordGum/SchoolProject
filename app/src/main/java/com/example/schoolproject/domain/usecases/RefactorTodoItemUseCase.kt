package com.example.schoolproject.domain.usecases

import com.example.schoolproject.domain.TodoItem
import com.example.schoolproject.domain.TodoItemsRepository

class RefactorTodoItemUseCase (
    private val repository: TodoItemsRepository
) {
    suspend operator fun invoke(todoItem: TodoItem) = repository.refactorTodoItem(todoItem)
}
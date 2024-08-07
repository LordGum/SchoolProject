package com.example.schoolproject.domain.usecases.database

import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.domain.TodoItemsRepository
import javax.inject.Inject

class RefactorTodoItemUseCase @Inject constructor(
    private val repository: TodoItemsRepository
) {
    suspend operator fun invoke(todoItem: TodoItem) = repository.refactorTodoItem(todoItem)
}
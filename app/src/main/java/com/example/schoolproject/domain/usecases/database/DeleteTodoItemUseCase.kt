package com.example.schoolproject.domain.usecases.database

import com.example.schoolproject.domain.TodoItemsRepository
import javax.inject.Inject

class DeleteTodoItemUseCase @Inject constructor(
    private val repository: TodoItemsRepository
) {
    suspend operator fun invoke(id: String) = repository.deleteTodoItem(id)
}
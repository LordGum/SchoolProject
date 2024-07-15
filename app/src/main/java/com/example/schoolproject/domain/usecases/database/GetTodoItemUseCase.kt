package com.example.schoolproject.domain.usecases.database

import com.example.schoolproject.domain.TodoItemsRepository
import javax.inject.Inject

class GetTodoItemUseCase @Inject constructor(
    private val repository: TodoItemsRepository
) {
    suspend operator fun invoke(id: String) = repository.getTodoItem(id)
}
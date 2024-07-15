package com.example.schoolproject.domain.usecases.database

import com.example.schoolproject.domain.TodoItemsRepository
import javax.inject.Inject

class GetTodoListUseCase @Inject constructor(
    private val repository: TodoItemsRepository
) {
    operator fun invoke() = repository.todoList
}
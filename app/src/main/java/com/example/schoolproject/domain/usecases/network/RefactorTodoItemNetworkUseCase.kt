package com.example.schoolproject.domain.usecases.network

import com.example.schoolproject.domain.NetworkRepository
import com.example.schoolproject.domain.entities.TodoItem

class RefactorTodoItemNetworkUseCase(
    private val repository: NetworkRepository
) {
    operator fun invoke(item: TodoItem) = repository.refactorTodoItem(item)
}
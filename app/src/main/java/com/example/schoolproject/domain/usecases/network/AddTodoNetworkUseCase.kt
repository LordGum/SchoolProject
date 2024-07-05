package com.example.schoolproject.domain.usecases.network

import com.example.schoolproject.domain.NetworkRepository
import com.example.schoolproject.domain.entities.TodoItem

class AddTodoNetworkUseCase(
    private val repository: NetworkRepository
) {
    suspend operator fun invoke(item: TodoItem) = repository.addTodoItem(item)
}
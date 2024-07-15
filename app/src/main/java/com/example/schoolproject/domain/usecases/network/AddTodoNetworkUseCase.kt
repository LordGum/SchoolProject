package com.example.schoolproject.domain.usecases.network

import com.example.schoolproject.domain.NetworkRepository
import com.example.schoolproject.domain.entities.TodoItem
import javax.inject.Inject

class AddTodoNetworkUseCase @Inject constructor(
    private val repository: NetworkRepository
) {
    suspend operator fun invoke(item: TodoItem) = repository.addTodoItem(item)
}
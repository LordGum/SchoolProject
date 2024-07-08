package com.example.schoolproject.domain.usecases.network

import com.example.schoolproject.domain.NetworkRepository

class DeleteTodoNetworkUseCase(
    private val repository: NetworkRepository
) {
    suspend operator fun invoke(id: String) = repository.deleteTodoItem(id)
}
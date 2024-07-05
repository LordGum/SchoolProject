package com.example.schoolproject.domain.usecases.network

import com.example.schoolproject.domain.NetworkRepository

class GetTodoNetworkUseCase(
    private val repository: NetworkRepository
) {
    suspend operator fun invoke(id: String) = repository.getTodoItem(id)
}
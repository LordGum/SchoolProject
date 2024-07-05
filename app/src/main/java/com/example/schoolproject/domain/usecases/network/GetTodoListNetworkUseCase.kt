package com.example.schoolproject.domain.usecases.network

import com.example.schoolproject.domain.NetworkRepository

class GetTodoListNetworkUseCase(
    private val repository: NetworkRepository
) {
    suspend operator fun invoke() = repository.getTodoList()
}
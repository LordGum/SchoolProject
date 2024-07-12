package com.example.schoolproject.domain.usecases.network

import com.example.schoolproject.domain.NetworkRepository
import javax.inject.Inject

class GetTodoListNetworkUseCase @Inject constructor(
    private val repository: NetworkRepository
) {
    suspend operator fun invoke() = repository.getTodoList()
}
package com.example.schoolproject.domain.usecases.network

import com.example.schoolproject.domain.NetworkRepository
import javax.inject.Inject

class DeleteTodoNetworkUseCase @Inject constructor(
    private val repository: NetworkRepository
) {
    suspend operator fun invoke(id: String) = repository.deleteTodoItem(id)
}
package com.example.schoolproject.domain.usecases.network

import com.example.schoolproject.domain.NetworkRepository
import com.example.schoolproject.domain.entities.AuthState
import kotlinx.coroutines.flow.StateFlow

class GetAuthStateUseCase (
    private val repository: NetworkRepository
) {
    operator fun invoke(): StateFlow<AuthState> {
        return repository.getAuthStateFlow()
    }
}
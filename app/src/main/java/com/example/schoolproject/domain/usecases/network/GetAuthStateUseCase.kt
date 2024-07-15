package com.example.schoolproject.domain.usecases.network

import com.example.schoolproject.domain.NetworkRepository
import com.example.schoolproject.domain.entities.AuthState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAuthStateUseCase @Inject constructor(
    private val repository: NetworkRepository
) {
    operator fun invoke(): Flow<AuthState> {
        return repository.getAuthStateFlow()
    }
}
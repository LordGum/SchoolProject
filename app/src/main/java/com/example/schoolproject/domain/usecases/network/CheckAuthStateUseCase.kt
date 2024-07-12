package com.example.schoolproject.domain.usecases.network

import com.example.schoolproject.domain.NetworkRepository
import javax.inject.Inject

class CheckAuthStateUseCase @Inject constructor(
    private val repository: NetworkRepository
) {
    suspend operator fun invoke() {
        repository.checkAuthState()
    }
}
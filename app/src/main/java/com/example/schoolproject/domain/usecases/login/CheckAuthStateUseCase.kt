package com.example.schoolproject.domain.usecases.login

import com.example.schoolproject.domain.NetworkRepository

class CheckAuthStateUseCase (
    private val repository: NetworkRepository
) {
    suspend operator fun invoke() {
        repository.checkAuthState()
    }
}
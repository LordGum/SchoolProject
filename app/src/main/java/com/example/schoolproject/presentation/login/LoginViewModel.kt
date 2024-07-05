package com.example.schoolproject.presentation.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolproject.data.NetworkRepositoryImpl
import com.example.schoolproject.data.network.TokenPreferences
import com.example.schoolproject.domain.usecases.login.CheckAuthStateUseCase
import com.example.schoolproject.domain.usecases.login.GetAuthStateUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    context: Application
) : AndroidViewModel(context) {

    private val repository = NetworkRepositoryImpl(context, TokenPreferences(context))

    private val getAuthStateFlowUseCase = GetAuthStateUseCase(repository)
    private val checkAuthStateUseCase = CheckAuthStateUseCase(repository)

    val authState = getAuthStateFlowUseCase()

    fun performAuthResult() {
        viewModelScope.launch {
            checkAuthStateUseCase()
        }
    }
}
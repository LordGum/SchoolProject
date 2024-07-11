package com.example.schoolproject.presentation.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolproject.data.NetworkRepositoryImpl
import com.example.schoolproject.domain.usecases.network.CheckAuthStateUseCase
import com.example.schoolproject.domain.usecases.network.GetAuthStateUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    context: Application
) : AndroidViewModel(context) {

    private val repository = NetworkRepositoryImpl(context)

    private val getAuthStateFlowUseCase = GetAuthStateUseCase(repository)
    private val checkAuthStateUseCase = CheckAuthStateUseCase(repository)

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("LoginViewModel", "Exception caught by exception handler")
    }
    private val coroutineContext = Dispatchers.IO + exceptionHandler

    val authState = getAuthStateFlowUseCase()

    fun performAuthResult() {
        viewModelScope.launch(coroutineContext) {
            checkAuthStateUseCase()
        }
    }
}
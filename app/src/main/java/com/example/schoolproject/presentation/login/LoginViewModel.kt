package com.example.schoolproject.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolproject.domain.usecases.network.CheckAuthStateUseCase
import com.example.schoolproject.domain.usecases.network.GetAuthStateUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    getAuthStateFlowUseCase: GetAuthStateUseCase,
    private val checkAuthStateUseCase: CheckAuthStateUseCase
) : ViewModel() {

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
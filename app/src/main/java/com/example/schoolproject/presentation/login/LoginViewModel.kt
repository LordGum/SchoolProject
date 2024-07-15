package com.example.schoolproject.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolproject.domain.entities.AuthState
import com.example.schoolproject.domain.usecases.network.CheckAuthStateUseCase
import com.example.schoolproject.domain.usecases.network.GetAuthStateUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    getAuthStateFlowUseCase: GetAuthStateUseCase,
    private val checkAuthStateUseCase: CheckAuthStateUseCase
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("LoginViewModel", "Exception caught by exception handler")
    }
    private val coroutineContext = Dispatchers.IO + exceptionHandler

    val authState = getAuthStateFlowUseCase().stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = AuthState.Initial
    )

    fun performAuthResult() {
        viewModelScope.launch(coroutineContext) {
            checkAuthStateUseCase()
        }
    }
}
package com.example.schoolproject.presentation.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolproject.data.NetworkRepositoryImpl
import com.example.schoolproject.data.TodoItemsRepositoryImpl
import com.example.schoolproject.data.utils.SyncInteract
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.domain.usecases.database.DeleteTodoItemUseCase
import com.example.schoolproject.domain.usecases.database.GetTodoListUseCase
import com.example.schoolproject.domain.usecases.database.RefactorTodoItemUseCase
import com.example.schoolproject.domain.usecases.network.DeleteTodoNetworkUseCase
import com.example.schoolproject.domain.usecases.network.RefactorTodoItemNetworkUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = TodoItemsRepositoryImpl(application)
    private val repositoryNetwork = NetworkRepositoryImpl(application)
    private val connectionManager = repositoryNetwork.connectionManager
    private val syncInteract = SyncInteract(repository, repositoryNetwork)

    private val getTodoListUseCase = GetTodoListUseCase(repository)
    private val deleteTodoItemUseCase = DeleteTodoItemUseCase(repository)
    private val refactorTodoItemUseCase = RefactorTodoItemUseCase(repository)

    private val deleteTodoItemNetworkUseCase = DeleteTodoNetworkUseCase(repositoryNetwork)
    private val refactorTodoItemNetworkUseCase = RefactorTodoItemNetworkUseCase(repositoryNetwork)

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("MainViewModel", "Exception caught by exception handler")
    }
    private val coroutineContext = Dispatchers.IO + exceptionHandler

    private val _internetState = MutableStateFlow(false)

    init {
        observeInternetConnection()
    }

    private val _screenState = MutableStateFlow<MainScreenState>(MainScreenState.TodoList())
    val screenState: StateFlow<MainScreenState> = combine(
        _screenState,
        getTodoListUseCase.invoke(),
        repositoryNetwork.errorState
    ) { state, list, error ->
        if (state is MainScreenState.TodoList) {
            state.copy(
                todoList = list,
                count = list.count { it.isCompleted },
                errorState = error
            )
        } else {
            state
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MainScreenState.Loading
    )


    private fun observeInternetConnection() {
        viewModelScope.launch(coroutineContext) {
            connectionManager.internetState.collect { isConnected ->
                _internetState.value = isConnected
                syncInteract.syncTasks()
            }
        }
    }


    fun deleteTodoItem(id: String) {
        viewModelScope.launch(coroutineContext) {
            deleteTodoItemUseCase(id).await()
            deleteTodoItemNetworkUseCase(id)
        }
    }

    fun doneTodoItem(todoItem: TodoItem) {
        viewModelScope.launch(coroutineContext) {
            refactorTodoItemUseCase(todoItem.copy(isCompleted = !todoItem.isCompleted))
            refactorTodoItemNetworkUseCase(todoItem.copy(isCompleted = !todoItem.isCompleted))
        }
    }

    fun refreshTodoList(): Deferred<Unit> {
        return viewModelScope.async(coroutineContext) {
            syncInteract.syncTasks()
        }
    }
}


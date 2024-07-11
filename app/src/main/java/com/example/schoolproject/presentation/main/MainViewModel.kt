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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = TodoItemsRepositoryImpl(application)
    private val repositoryNetwork = NetworkRepositoryImpl(application)
    private val connectionManager = repositoryNetwork.connectionManager

    private val internetState = connectionManager.internetState
    private val syncInteract = SyncInteract(repository, repositoryNetwork)

    private val getTodoListUseCase = GetTodoListUseCase(repository)
    private val deleteTodoItemUseCase = DeleteTodoItemUseCase(repository)
    private val refactorTodoItemUseCase = RefactorTodoItemUseCase(repository)

    private val deleteTodoItemNetworkUseCase = DeleteTodoNetworkUseCase(repositoryNetwork)
    private val refactorTodoItemNetworkUseCase = RefactorTodoItemNetworkUseCase(repositoryNetwork)

    private val _count = MutableStateFlow(0)

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("MainViewModel", "Exception caught by exception handler")
    }
    private val coroutineContext = Dispatchers.IO + exceptionHandler

    val errorState = repositoryNetwork.errorState

    val screenState = getTodoListUseCase()
        .onEach { list -> _count.value = list.count { it.isCompleted } }
        .map {
            MainScreenState.TodoList(
                todoList = it,
                _count.value,
                errorState.value
            ) as MainScreenState
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
            if (internetState.value) {
                syncInteract.syncTasks()
            } else delay(2000)
        }
    }
}


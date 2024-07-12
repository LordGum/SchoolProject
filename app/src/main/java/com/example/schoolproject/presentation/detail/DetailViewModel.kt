package com.example.schoolproject.presentation.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolproject.data.NetworkRepositoryImpl
import com.example.schoolproject.data.TodoItemsRepositoryImpl
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.domain.usecases.database.AddTodoItemUseCase
import com.example.schoolproject.domain.usecases.database.DeleteTodoItemUseCase
import com.example.schoolproject.domain.usecases.database.GetTodoItemUseCase
import com.example.schoolproject.domain.usecases.network.AddTodoNetworkUseCase
import com.example.schoolproject.domain.usecases.network.DeleteTodoNetworkUseCase
import com.example.schoolproject.domain.usecases.network.RefactorTodoItemNetworkUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class DetailViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d(
            "DetailViewModel",
            "Exception caught by exception handler. error = ${throwable.message}"
        )
        _screenState.value = DetailScreenState.ErrorState
    }
    private val coroutineContext = Dispatchers.IO + exceptionHandler

    private val repository = TodoItemsRepositoryImpl(application)
    private val repositoryNetwork = NetworkRepositoryImpl(application)

    private val getTodoItemUseCase = GetTodoItemUseCase(repository)
    private val addTodoItemUseCase = AddTodoItemUseCase(repository)
    private val deleteTodoItemUseCase = DeleteTodoItemUseCase(repository)

    private val addTodoItemNetworkUseCase = AddTodoNetworkUseCase(repositoryNetwork)
    private val deleteTodoItemNetworkUseCase = DeleteTodoNetworkUseCase(repositoryNetwork)
    private val refactorTodoItemNetworkUseCase = RefactorTodoItemNetworkUseCase(repositoryNetwork)

    private val _screenState = MutableStateFlow<DetailScreenState>(DetailScreenState.LoadingState)
    val screenState: StateFlow<DetailScreenState> = _screenState

    fun getTodoItem(id: String) {
        viewModelScope.launch(coroutineContext) {
            if (id != TodoItem.UNDEFINED_ID) {
                val item = getTodoItemUseCase(id)
                _screenState.value = DetailScreenState.RefactorTodoItemState(item)
            } else {
                val item = TodoItem(
                    text = "",
                    priority = TodoItem.Priority.NORMAL,
                    isCompleted = false,
                    createdDate = Date()
                )
                _screenState.value = DetailScreenState.AddTodoItemState(item)
            }
        }
    }

    fun saveTodoItem(todoItem: TodoItem) {
        viewModelScope.launch(coroutineContext) {
            addTodoItemUseCase(todoItem).await()
            addTodoItemNetworkUseCase(todoItem)
        }
    }

    fun refactorTodoItem(todoItem: TodoItem) {
        viewModelScope.launch(coroutineContext) {
            Log.d("tag", "before refactor")
            addTodoItemUseCase(todoItem).await()
            Log.d("tag", "middle refactor")
            refactorTodoItemNetworkUseCase(todoItem)
            Log.d("tag", "after refactor")
        }
    }

    fun deleteTodoItem(id: String) {
        viewModelScope.launch(coroutineContext) {
            deleteTodoItemUseCase(id).await()
            deleteTodoItemNetworkUseCase(id)
        }
    }
}
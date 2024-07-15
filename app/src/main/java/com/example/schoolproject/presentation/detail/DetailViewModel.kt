package com.example.schoolproject.presentation.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import javax.inject.Inject

class DetailViewModel @Inject constructor (
    private val getTodoItemUseCase: GetTodoItemUseCase,
    private val addTodoItemUseCase: AddTodoItemUseCase,
    private val deleteTodoItemUseCase: DeleteTodoItemUseCase,
    private val addTodoItemNetworkUseCase: AddTodoNetworkUseCase,
    private val deleteTodoItemNetworkUseCase: DeleteTodoNetworkUseCase,
    private val refactorTodoItemNetworkUseCase: RefactorTodoItemNetworkUseCase,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d(
            "DetailViewModel",
            "Exception caught by exception handler. error = ${throwable.message}"
        )
        _screenState.value = DetailScreenState.ErrorState
    }
    private val coroutineContext = Dispatchers.IO + exceptionHandler

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
            addTodoItemUseCase(todoItem).await()
            refactorTodoItemNetworkUseCase(todoItem)
        }
    }

    fun deleteTodoItem(id: String) {
        viewModelScope.launch(coroutineContext) {
            deleteTodoItemUseCase(id).await()
            deleteTodoItemNetworkUseCase(id)
        }
    }
}
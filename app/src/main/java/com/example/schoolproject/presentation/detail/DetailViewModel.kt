package com.example.schoolproject.presentation.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolproject.data.TodoItemsRepositoryImpl
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.domain.usecases.AddTodoItemUseCase
import com.example.schoolproject.domain.usecases.DeleteTodoItemUseCase
import com.example.schoolproject.domain.usecases.GetTodoItemUseCase
import com.example.schoolproject.domain.usecases.RefactorTodoItemUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class DetailViewModel (
    application: Application
): AndroidViewModel(application) {
    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("DetailViewModel", "Exception caught by exception handler")
        _screenState.value = DetailScreenState.ErrorState
    }

    private val repository = TodoItemsRepositoryImpl(application)

    private val getTodoItemUseCase = GetTodoItemUseCase(repository)
    private val addTodoItemUseCase = AddTodoItemUseCase(repository)
    private val deleteTodoItemUseCase = DeleteTodoItemUseCase(repository)
    private val refactorTodoItemUseCase = RefactorTodoItemUseCase(repository)

    private val _screenState = MutableStateFlow<DetailScreenState>(DetailScreenState.LoadingState)
    val screenState: StateFlow<DetailScreenState> = _screenState

    fun getTodoItem(id: String) {
        viewModelScope.launch(exceptionHandler) {
            val item =  if (id != TodoItem.UNDEFINED_ID ) getTodoItemUseCase(id) else {
                TodoItem(
                    text = "",
                    priority = TodoItem.Priority.NORMAL,
                    isCompleted = false,
                    createdDate = Date()
                )
            }
            _screenState.value = DetailScreenState.TodoItemState(item)
        }
    }

    fun saveTodoItem(todoItem: TodoItem) {
        viewModelScope.launch(exceptionHandler) {
            addTodoItemUseCase(todoItem)
        }
    }

    fun refactorToDoItem(todoItem: TodoItem) {
        viewModelScope.launch(exceptionHandler) {
            refactorTodoItemUseCase(todoItem)
        }
    }

    fun deleteTodoItem(id: String) {
        viewModelScope.launch(exceptionHandler) {
            deleteTodoItemUseCase(id)
        }
    }
}
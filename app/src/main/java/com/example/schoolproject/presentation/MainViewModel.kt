package com.example.schoolproject.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.schoolproject.data.TodoItemsRepositoryImpl
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.domain.usecases.AddTodoItemUseCase
import com.example.schoolproject.domain.usecases.DeleteTodoItemUseCase
import com.example.schoolproject.domain.usecases.GetTodoListUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = TodoItemsRepositoryImpl(application)

    private val getTodoListUseCase = GetTodoListUseCase(repository)
    private val addTodoItemUseCase = AddTodoItemUseCase(repository)
    private val deleteTodoItemUseCase = DeleteTodoItemUseCase(repository)

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("MainViewModel", "Exception caught by exception handler")
    }
    private val scope = CoroutineScope(Dispatchers.IO)

    val screenState = getTodoListUseCase()
        .filter { it.isNotEmpty() }
        .map { MainScreenState.TodoList(todoList = it) as MainScreenState }

    fun addTodoItem(todoItem: TodoItem) {
        scope.launch(exceptionHandler) {
            addTodoItemUseCase(todoItem)
        }
    }

    fun deleteTodoItem(id: String) {
        scope.launch(exceptionHandler) {
            deleteTodoItemUseCase(id)
        }
    }
}
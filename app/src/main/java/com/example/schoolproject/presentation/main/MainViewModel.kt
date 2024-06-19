package com.example.schoolproject.presentation.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.schoolproject.data.TodoItemsRepositoryImpl
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.domain.usecases.AddTodoItemUseCase
import com.example.schoolproject.domain.usecases.DeleteTodoItemUseCase
import com.example.schoolproject.domain.usecases.GetTodoListUseCase
import com.example.schoolproject.domain.usecases.RefactorTodoItemUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application
): AndroidViewModel(application) {

    private val repository = TodoItemsRepositoryImpl(application)

    private val getTodoListUseCase = GetTodoListUseCase(repository)
    private val addTodoItemUseCase = AddTodoItemUseCase(repository)
    private val deleteTodoItemUseCase = DeleteTodoItemUseCase(repository)
    private val refactorTodoItemUseCase = RefactorTodoItemUseCase(repository)

    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("MainViewModel", "Exception caught by exception handler")
    }

    val screenState = getTodoListUseCase()
        .filter { it.isNotEmpty() }
        .map { MainScreenState.TodoList(todoList = it) as MainScreenState }

    fun addTodoItem(todoItem: TodoItem) {
        viewModelScope.launch(exceptionHandler) {
            addTodoItemUseCase(todoItem)
        }
    }

    fun deleteTodoItem(id: Int) {
        viewModelScope.launch(exceptionHandler) {
            deleteTodoItemUseCase(id)
        }
    }

    fun doneTodoItem(todoItem: TodoItem) {
        if (todoItem.isCompleted) { if (_count.value > 0) _count.value-- }
        else _count.value++
        viewModelScope.launch(exceptionHandler) {
            refactorTodoItemUseCase(todoItem = todoItem.copy(isCompleted = !todoItem.isCompleted))
        }
    }
}
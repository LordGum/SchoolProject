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
import kotlinx.coroutines.flow.onEach
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

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("MainViewModel", "Exception caught by exception handler")
    }

    val screenState = getTodoListUseCase()
        .filter { it.isNotEmpty() }
        .onEach { list -> _count.value = list.count{ it.isCompleted } }
        .map { MainScreenState.TodoList(todoList = it, _count.value) as MainScreenState }


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
        viewModelScope.launch(exceptionHandler) {
            refactorTodoItemUseCase(todoItem = todoItem.copy(isCompleted = !todoItem.isCompleted))
        }
    }
}
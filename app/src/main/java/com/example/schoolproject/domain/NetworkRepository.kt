package com.example.schoolproject.domain

import com.example.schoolproject.data.network.model.ElementDto
import com.example.schoolproject.domain.entities.AuthState
import com.example.schoolproject.domain.entities.ErrorState
import com.example.schoolproject.domain.entities.TodoItem
import kotlinx.coroutines.flow.StateFlow

interface NetworkRepository {

    val errorState: StateFlow<ErrorState?>

    val todoList: StateFlow<List<ElementDto>>

    fun getAuthStateFlow(): StateFlow<AuthState>

    suspend fun checkAuthState()

    suspend fun getTodoList()

    suspend fun deleteTodoItem(id: String)

    suspend fun addTodoItem(todoItem: TodoItem)

    suspend fun refactorTodoItem(todoItem: TodoItem)

    suspend fun refreshTodoItemList(list: List<TodoItem>)
}
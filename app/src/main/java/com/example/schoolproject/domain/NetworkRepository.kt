package com.example.schoolproject.domain

import com.example.schoolproject.data.network.model.ResponseListDto
import com.example.schoolproject.domain.entities.AuthState
import com.example.schoolproject.domain.entities.TodoItem
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.StateFlow

interface NetworkRepository {

    fun getAuthStateFlow(): StateFlow<AuthState>

    suspend fun checkAuthState()

    fun getTodoList(): Deferred<ResponseListDto>

    fun deleteTodoItem(id: String)

    fun addTodoItem(todoItem: TodoItem)

    fun refactorTodoItem(todoItem: TodoItem)
}
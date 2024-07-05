package com.example.schoolproject.domain

import com.example.schoolproject.data.database.TodoItemDbModel
import com.example.schoolproject.data.network.model.ResponseListDto
import com.example.schoolproject.domain.entities.AuthState
import com.example.schoolproject.domain.entities.TodoItem
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.Deferred

interface NetworkRepository {

    fun getAuthStateFlow(): StateFlow<AuthState>

    suspend fun checkAuthState()

    suspend fun getTodoList(): Deferred<ResponseListDto>

    suspend fun getTodoItem(id: String): TodoItem

    suspend fun deleteTodoItem(id: String)

    suspend fun addTodoItem(todoItem: TodoItem)
}
package com.example.schoolproject.domain

import kotlinx.coroutines.flow.Flow

interface TodoItemsRepository  {

    val todoList: Flow<List<TodoItem>>

    suspend fun addTodoItem(todoItem: TodoItem)

    suspend fun deleteTodoItem(id: String)

    suspend fun getTodoItem(id: String): TodoItem

    suspend fun refactorTodoItem(todoItem: TodoItem)
}
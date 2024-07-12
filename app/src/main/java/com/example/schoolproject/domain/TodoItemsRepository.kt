package com.example.schoolproject.domain

import com.example.schoolproject.domain.entities.TodoItem
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow

interface TodoItemsRepository  {

    val todoList: Flow<List<TodoItem>>

    fun getTodoList(): List<TodoItem>

    suspend fun deleteTodoList()

    suspend fun addTodoList(todoList: List<TodoItem>)

    suspend fun addTodoItem(todoItem: TodoItem): Deferred<Unit>

    suspend fun deleteTodoItem(id: String): Deferred<Unit>

    suspend fun getTodoItem(id: String): TodoItem

    suspend fun refactorTodoItem(todoItem: TodoItem)
}
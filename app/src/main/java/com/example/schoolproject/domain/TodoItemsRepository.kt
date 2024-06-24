package com.example.schoolproject.domain

import com.example.schoolproject.domain.entities.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoItemsRepository  {

    val todoList: Flow<List<TodoItem>>

    suspend fun addTodoItem(todoItem: TodoItem)

    suspend fun deleteTodoItem(id: Int)

    suspend fun getTodoItem(id: Int): TodoItem

    suspend fun refactorTodoItem(todoItem: TodoItem)
}
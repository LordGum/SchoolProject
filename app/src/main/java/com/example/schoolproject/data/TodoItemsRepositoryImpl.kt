package com.example.schoolproject.data

import android.content.Context
import com.example.schoolproject.data.database.AppDatabase
import com.example.schoolproject.domain.TodoItemsRepository
import com.example.schoolproject.domain.entities.TodoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoItemsRepositoryImpl(
    private val context: Context
): TodoItemsRepository {

    private val todoDao = AppDatabase.getInstance(context).todoDao()
    private val mapper = Mapper()

    override val todoList: Flow<List<TodoItem>> = todoDao.getTodoList().map {
        it.map { dbModel ->
            mapper.dbModelToEntity(dbModel)
        }
    }

    override suspend fun addTodoItem(todoItem: TodoItem) {
        todoDao.addTodoItem(mapper.entityToDbModel(todoItem))
    }

    override suspend fun deleteTodoItem(id: String) {
        todoDao.deleteTodoItem(id)
    }

    override suspend fun getTodoItem(id: String): TodoItem {
        val dbModel = todoDao.getTodoItemInfo(id)
        return mapper.dbModelToEntity(dbModel)
    }

    override suspend fun refactorTodoItem(todoItem: TodoItem) {
        todoDao.addTodoItem(mapper.entityToDbModel(todoItem))
    }
}
package com.example.schoolproject.data

import android.content.Context
import com.example.schoolproject.data.database.AppDatabase
import com.example.schoolproject.data.database.TodoItemDbModel
import com.example.schoolproject.data.mappers.MapperDb
import com.example.schoolproject.domain.TodoItemsRepository
import com.example.schoolproject.domain.entities.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoItemsRepositoryImpl(
    context: Context
): TodoItemsRepository {

    private val todoDao = AppDatabase.getInstance(context).todoDao()
    private val mapper = MapperDb()
    private val scope = CoroutineScope(Dispatchers.IO)

    override val todoList: Flow<List<TodoItem>> = todoDao.getTodoList().map {
        it.map { dbModel ->
            mapper.dbModelToEntity(dbModel)
        }
    }

    override fun getTodoList(): List<TodoItemDbModel> {
        return todoDao.getAllItems()
    }

    override suspend fun deleteTodoList() {
        todoDao.deleteAllItems()
    }

    override suspend fun addTodoList(todoList: List<TodoItem>) {
        todoDao.insertAll( todoList.map { mapper.entityToDbModel(it) })
    }

    override suspend fun addTodoItem(todoItem: TodoItem): Deferred<Unit> {
        return scope.async {
            todoDao.addTodoItem(mapper.entityToDbModel(todoItem))
        }
    }

    override suspend fun deleteTodoItem(id: String): Deferred<Unit> {
        return scope.async {
            todoDao.deleteTodoItem(id)
        }
    }

    override suspend fun getTodoItem(id: String): TodoItem {
        val dbModel = todoDao.getTodoItemInfo(id)
        return mapper.dbModelToEntity(dbModel)
    }

    override suspend fun refactorTodoItem(todoItem: TodoItem) {
        todoDao.addTodoItem(mapper.entityToDbModel(todoItem))
    }
}
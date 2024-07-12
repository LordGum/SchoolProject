package com.example.schoolproject.data

import com.example.schoolproject.data.database.TodoListDao
import com.example.schoolproject.data.utils.mappers.MapperDb
import com.example.schoolproject.domain.TodoItemsRepository
import com.example.schoolproject.domain.entities.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoItemsRepositoryImpl @Inject constructor(
    private val todoDao: TodoListDao,
    private val mapper: MapperDb
) : TodoItemsRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    override val todoList: Flow<List<TodoItem>> = todoDao.getTodoList().map {
        it.map { dbModel ->
            mapper.dbModelToEntity(dbModel)
        }
    }

    override fun getTodoList(): List<TodoItem> {
        return todoDao.getAllItems().map {
            mapper.dbModelToEntity(it)
        }
    }

    override suspend fun deleteTodoList() {
        todoDao.deleteAllItems()
    }

    override suspend fun addTodoList(todoList: List<TodoItem>) {
        todoDao.insertAll(todoList.map { mapper.entityToDbModel(it) })
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
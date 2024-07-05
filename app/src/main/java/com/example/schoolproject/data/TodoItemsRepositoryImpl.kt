package com.example.schoolproject.data

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.schoolproject.data.database.AppDatabase
import com.example.schoolproject.data.database.TodoItemDbModel
import com.example.schoolproject.data.mappers.MapperDb
import com.example.schoolproject.data.network.TokenPreferences
import com.example.schoolproject.data.utils.ListItemDiffCallback
import com.example.schoolproject.domain.TodoItemsRepository
import com.example.schoolproject.domain.entities.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

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

    override suspend fun addTodoItem(todoItem: TodoItem): Deferred<Unit> {
        return scope.async {
            todoDao.addTodoItem(mapper.entityToDbModel(todoItem))
        }
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

    suspend fun updateDatabase(newList: List<TodoItemDbModel>) {

        val oldList = todoDao.getAllItems()
        val diffResult = DiffUtil.calculateDiff(
            ListItemDiffCallback(oldList, newList)
        )

        diffResult.dispatchUpdatesTo(object : ListUpdateCallback {
            override fun onChanged(position: Int, count: Int, payload: Any?) {
                val item = newList[position]
                scope.launch {
                    todoDao.addTodoItem(item)
                }
            }

            override fun onInserted(position: Int, count: Int) {
                val item = newList[position]
                scope.launch {
                    todoDao.addTodoItem(item)
                }
            }

            override fun onRemoved(position: Int, count: Int) {
                val item = oldList[position]
                scope.launch {
                    todoDao.deleteTodoItem(item.id)
                }
            }

            override fun onMoved(fromPosition: Int, toPosition: Int) {}
        })
    }
}
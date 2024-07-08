package com.example.schoolproject.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodoItem(todoItem: TodoItemDbModel)

    @Query("DELETE FROM todolist WHERE id = :id")
    suspend fun deleteTodoItem(id: String)

    @Query("SELECT * FROM todolist WHERE id = :id")
    suspend fun getTodoItemInfo(id: String): TodoItemDbModel

    @Query("SELECT * FROM todolist ORDER BY createdDate DESC")
    fun getTodoList(): Flow<List<TodoItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<TodoItemDbModel>)

    @Query("SELECT * FROM todolist")
     fun getAllItems(): List<TodoItemDbModel>

    @Query("DELETE FROM todolist")
    suspend fun deleteAllItems()
}
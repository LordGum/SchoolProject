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
    suspend fun deleteTodoItem(id: Int)

    @Query("SELECT * FROM todolist WHERE id = :id")
    suspend fun getTodoItemInfo(id: Int): TodoItemDbModel

    @Query("SELECT * FROM todolist ORDER BY id")
    fun getTodoList(): Flow<List<TodoItemDbModel>>
}
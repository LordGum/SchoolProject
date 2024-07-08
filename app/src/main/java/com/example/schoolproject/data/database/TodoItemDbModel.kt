package com.example.schoolproject.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.schoolproject.domain.entities.TodoItem
import java.util.Date

@Entity(tableName = "todolist")
data class TodoItemDbModel(
    @PrimaryKey
    val id: String,
    var text: String,
    val priority: TodoItem.Priority,
    var isCompleted: Boolean,
    val createdDate: Date,
    var modifiedDate: Date? = null,
    val deadline: Date? = null
)
package com.example.schoolproject.data.mappers

import com.example.schoolproject.data.database.TodoItemDbModel
import com.example.schoolproject.domain.entities.TodoItem

class Mapper {
    fun entityToDbModel(todoItem: TodoItem): TodoItemDbModel {
        return TodoItemDbModel(
            id = todoItem.id,
            text = todoItem.text,
            priority = todoItem.priority,
            isCompleted = todoItem.isCompleted,
            createdDate = todoItem.createdDate,
            modifiedDate = todoItem.modifiedDate,
            deadline = todoItem.deadline
        )
    }

    fun dbModelToEntity(todoItemDbModel: TodoItemDbModel): TodoItem {
        return TodoItem(
            id = todoItemDbModel.id,
            text = todoItemDbModel.text,
            priority = todoItemDbModel.priority,
            isCompleted = todoItemDbModel.isCompleted,
            createdDate = todoItemDbModel.createdDate,
            modifiedDate = todoItemDbModel.modifiedDate,
            deadline = todoItemDbModel.deadline
        )
    }
}
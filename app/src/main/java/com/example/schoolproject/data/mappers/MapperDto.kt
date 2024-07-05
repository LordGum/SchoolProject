package com.example.schoolproject.data.mappers

import com.example.schoolproject.data.database.TodoItemDbModel
import com.example.schoolproject.data.network.model.ElementDto
import com.example.schoolproject.data.network.model.ImportanceDto
import com.example.schoolproject.data.network.model.ResponseListDto
import com.example.schoolproject.domain.entities.TodoItem
import java.util.Date

class MapperDto {

    fun mapResponseDtoTodoListDb(response: ResponseListDto): List<TodoItemDbModel> {
        return response.list.map { mapElementToTodoItemDb(it) }
    }

    private fun mapElementToTodoItemDb(elementDto: ElementDto): TodoItemDbModel {
        return TodoItemDbModel(
            id = elementDto.id,
            text = elementDto.text,
            priority = mapImportanceToPriority(elementDto.importance),
            isCompleted = elementDto.done,
            createdDate = Date(elementDto.createdAt),
            modifiedDate = Date(elementDto.lastUpdatedBy),
            deadline = if (elementDto.deadline != null) Date(elementDto.deadline) else null
        )
    }

    private fun mapImportanceToPriority(level: ImportanceDto): TodoItem.Priority {
        return when(level) {
            ImportanceDto.low -> TodoItem.Priority.LOW
            ImportanceDto.basic -> TodoItem.Priority.NORMAL
            ImportanceDto.important -> TodoItem.Priority.HIGH
        }
    }

}
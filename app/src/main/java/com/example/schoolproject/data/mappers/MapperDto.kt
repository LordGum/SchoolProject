package com.example.schoolproject.data.mappers

import com.example.schoolproject.data.network.model.ElementDto
import com.example.schoolproject.data.network.model.ImportanceDto
import com.example.schoolproject.domain.entities.TodoItem
import java.util.Date

class MapperDto {

    private fun mapImportanceToPriority(level: ImportanceDto): TodoItem.Priority {
        return when(level) {
            ImportanceDto.low -> TodoItem.Priority.LOW
            ImportanceDto.basic -> TodoItem.Priority.NORMAL
            ImportanceDto.important -> TodoItem.Priority.HIGH
        }
    }

    fun mapEntityToElement(todoItem: TodoItem): ElementDto {
        return ElementDto(
            id = todoItem.id,
            text = todoItem.text,
            importance = mapPriorityToImportance(todoItem.priority),
            deadline = todoItem.deadline?.time,
            done = todoItem.isCompleted,
            createdAt = todoItem.createdDate.time,
            changeAt = todoItem.modifiedDate?.time ?: todoItem.createdDate.time,
            lastUpdatedBy = "CLIENT_DEVICE_ID",
            color = null
        )
    }

    private fun mapPriorityToImportance(priority: TodoItem.Priority): ImportanceDto {
        return when(priority) {
            TodoItem.Priority.LOW -> ImportanceDto.low
            TodoItem.Priority.NORMAL -> ImportanceDto.basic
            TodoItem.Priority.HIGH -> ImportanceDto.important
        }
    }

    fun mapElementToEntity(elementDto: ElementDto): TodoItem {
        return TodoItem(
            id = elementDto.id,
            text = elementDto.text,
            priority = mapImportanceToPriority(elementDto.importance),
            isCompleted = elementDto.done,
            createdDate = Date(elementDto.createdAt),
            modifiedDate = Date(elementDto.changeAt),
            deadline = if(elementDto.deadline != null) Date(elementDto.deadline) else null
        )
    }

}
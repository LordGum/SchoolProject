package com.example.schoolproject.domain.entities

import java.util.Date

data class TodoItem(
    val id: String = UNDEFINED_ID,
    var text: String,
    val priority: Priority,
    var isCompleted: Boolean,
    val createdDate: Date,
    var modifiedDate: Date? = null,
    val deadline: Date? = null
) {
    enum class Priority {
        LOW, NORMAL, HIGH
    }
    companion object {
        const val UNDEFINED_ID = "0"
    }
}
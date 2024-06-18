package com.example.schoolproject.domain

import java.util.Date

data class TodoItem(
    val id: String,
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
}
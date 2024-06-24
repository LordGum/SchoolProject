package com.example.schoolproject.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class TodoItem(
    val id: Int = UNDEFINED_ID,
    var text: String,
    val priority: Priority,
    var isCompleted: Boolean,
    val createdDate: Date,
    var modifiedDate: Date? = null,
    val deadline: Date? = null
) : Parcelable {
    enum class Priority {
        LOW, NORMAL, HIGH
    }
    companion object {
        const val UNDEFINED_ID = 0
    }
}
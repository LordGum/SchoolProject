package com.example.schoolproject.data.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.schoolproject.data.database.TodoItemDbModel

class ListItemDiffCallback(
    private val oldList: List<TodoItemDbModel>,
    private val newList: List<TodoItemDbModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}


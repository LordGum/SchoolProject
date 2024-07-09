package com.example.schoolproject.presentation.ui_elements

import com.example.schoolproject.domain.entities.TodoItem
import java.util.Date

class PreviewData {
    companion object {

        fun getPreviewTodoItem(): TodoItem {
            return TodoItem(
                "3",
                "Слушать песни Майли про Малибу и мечтать о море",
                TodoItem.Priority.HIGH,
                createdDate = Date(),
                isCompleted = false,
                deadline = Date()
            )
        }

        fun getPreviewData(): ArrayList<TodoItem> {
            val list = ArrayList<TodoItem>()
            list.add(
                TodoItem(
                    "0",
                    "Устроить дискотеку в стиле 90х",
                    priority = TodoItem.Priority.NORMAL,
                    createdDate = Date(),
                    isCompleted = true
                )
            )
            list.add(
                TodoItem(
                    "2",
                    "Сдать анализы до обеда",
                    TodoItem.Priority.HIGH,
                    createdDate = Date(),
                    isCompleted = false
                )
            )
            list.add(
                TodoItem(
                    "3",
                    "Слушать песни Майли про Малибу",
                    TodoItem.Priority.NORMAL,
                    createdDate = Date(),
                    isCompleted = false,
                    deadline = Date()
                )
            )
            list.add(
                TodoItem(
                    "4",
                    "Забрать поссылку",
                    TodoItem.Priority.HIGH,
                    createdDate = Date(),
                    isCompleted = true
                )
            )
            list.add(
                TodoItem(
                    "5",
                    "генеральная уборка",
                    TodoItem.Priority.LOW,
                    createdDate = Date(),
                    isCompleted = true,
                    deadline = Date()
                )
            )
            list.add(
                TodoItem(
                    "6",
                    "Attend team meeting",
                    TodoItem.Priority.NORMAL,
                    createdDate = Date(),
                    isCompleted = false,
                    deadline = Date()
                )
            )
            list.add(
                TodoItem(
                    "8",
                    "купить байк и шлем",
                    TodoItem.Priority.LOW,
                    isCompleted = true,
                    createdDate = Date()
                )
            )
            list.add(
                TodoItem(
                    "9",
                    "выучить французский за 2 часа",
                    TodoItem.Priority.NORMAL,
                    isCompleted = false,
                    createdDate = Date()
                )
            )
            list.add(
                TodoItem(
                    "10",
                    "планировать отпуск",
                    TodoItem.Priority.LOW,
                    createdDate = Date(),
                    isCompleted = false
                )
            )
            list.add(
                TodoItem(
                    "11",
                    "сделать доклад",
                    TodoItem.Priority.HIGH,
                    createdDate = Date(),
                    isCompleted = false
                )
            )
            list.add(
                TodoItem(
                    "12",
                    "убрать комнату",
                    TodoItem.Priority.NORMAL,
                    createdDate = Date(),
                    isCompleted = false
                )
            )
            list.add(
                TodoItem(
                    "13",
                    "заплатить за аренду",
                    TodoItem.Priority.HIGH,
                    createdDate = Date(),
                    isCompleted = false
                )
            )

            return list
        }
    }
}
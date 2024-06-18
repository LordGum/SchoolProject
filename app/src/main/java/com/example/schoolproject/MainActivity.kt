package com.example.schoolproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.schoolproject.data.TodoItemsRepositoryImpl
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.ui.theme.SchoolProjectTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val list = ArrayList<TodoItem>()
        list.add(TodoItem("0", "hello", TodoItem.Priority.HIGH, true, Date(45678845356)))
        list.add(TodoItem("1", "hello 2", TodoItem.Priority.NORMAL, false, Date(40978845356)))


        val repository  = TodoItemsRepositoryImpl(applicationContext)
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            repository.addTodoItem(TodoItem("0", "hello", TodoItem.Priority.HIGH, true, Date(45678845356)))
            repository.addTodoItem(TodoItem("1", "hello 2", TodoItem.Priority.NORMAL, false, Date(40978845356)))
            Log.d("MATAG", repository.getTodoItem("1").createdDate.toString())
        }


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SchoolProjectTheme {
                //start commit
            }
        }
    }
}


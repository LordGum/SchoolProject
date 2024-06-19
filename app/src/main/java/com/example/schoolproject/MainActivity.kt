package com.example.schoolproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.schoolproject.data.TodoItemsRepositoryImpl
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.presentation.BaseScreen
import com.example.schoolproject.presentation.main.MainViewModel
import com.example.schoolproject.ui.theme.SchoolProjectTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository  = TodoItemsRepositoryImpl(applicationContext)
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            repository.addTodoItem(
                TodoItem(
                text = "hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello",
                priority = TodoItem.Priority.NORMAL,
                isCompleted = false,
                createdDate = Date(40978845356)
                )
            )
            repository.addTodoItem(
                TodoItem(
                    text = "hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello",
                    priority = TodoItem.Priority.NORMAL,
                    isCompleted = true,
                    createdDate = Date(40978845356),
                    deadline = Date()
                ))
        }

        setContent {
            val viewModel = MainViewModel(application)
            SchoolProjectTheme {
                BaseScreen()
//                MainScreen(
//                    viewModel,
//                    onTodoItemClick = {
//                        Log.d("MATAG", "adding new Item")
//                    }
//                )
            }
        }
    }
}


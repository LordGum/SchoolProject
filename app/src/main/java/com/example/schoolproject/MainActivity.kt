package com.example.schoolproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.schoolproject.data.TodoItemsRepositoryImpl
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.presentation.Item
import com.example.schoolproject.presentation.MainScreen
import com.example.schoolproject.presentation.MainScreenContent
import com.example.schoolproject.presentation.MainViewModel
import com.example.schoolproject.ui.theme.AppTheme
import com.example.schoolproject.ui.theme.SchoolProjectTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel = MainViewModel(application)
            SchoolProjectTheme {
                MainScreen(
                    viewModel,
                    onTodoItemClick = {
                        Log.d("MATAG", "adding new Item")
                    }
                )
            }
        }
    }
}


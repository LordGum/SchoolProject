package com.example.schoolproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.schoolproject.presentation.BaseScreen
import com.example.schoolproject.ui.theme.SchoolProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SchoolProjectTheme {
                BaseScreen()
            }
        }
    }
}


package com.example.schoolproject.presentation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.example.schoolproject.navigation.AppNavGraph
import com.example.schoolproject.navigation.rememberNavigationState
import com.example.schoolproject.presentation.detail.DetailScreen
import com.example.schoolproject.presentation.detail.DetailViewModel
import com.example.schoolproject.presentation.main.MainScreen
import com.example.schoolproject.presentation.main.MainViewModel

@Composable
fun BaseScreen() {
    val navigationState = rememberNavigationState()

    AppNavGraph(
        navHostController = navigationState.navHostController,
        enterScreenContent = {

        },
        mainScreenContent = {
            val viewModel: MainViewModel = ViewModelProvider(LocalContext.current as ComponentActivity)[MainViewModel::class]
            MainScreen(
                viewModel = viewModel,
                onTodoItemClick = {
                    navigationState.navigateToDetailScreen(it.id)
                }
            )
        },
        detailScreenContent = { id ->
            val viewModel = ViewModelProvider(LocalContext.current as ComponentActivity)[DetailViewModel::class.java]
            DetailScreen(
                id = id,
                viewModel = viewModel,
                onBackClickListener = {
                    navigationState.navHostController.popBackStack()
                },
                onSaveClickListener = {
                    viewModel.saveTodoItem(it)
                    navigationState.navHostController.popBackStack()
                }
            )
        }
    )
}
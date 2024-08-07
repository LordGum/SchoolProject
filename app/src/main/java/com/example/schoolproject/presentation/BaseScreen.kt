package com.example.schoolproject.presentation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.example.schoolproject.ViewModelFactory
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.navigation.AppNavGraph
import com.example.schoolproject.navigation.rememberNavigationState
import com.example.schoolproject.presentation.detail.DetailViewModel
import com.example.schoolproject.presentation.detail.ui.DetailScreen
import com.example.schoolproject.presentation.main.MainScreen
import com.example.schoolproject.presentation.main.MainViewModel

@Composable
fun BaseScreen(
    viewModelFactory: ViewModelFactory,
    isDark: Boolean,
    changeTheme: (Boolean) -> Unit
) {
    val navigationState = rememberNavigationState()
    val context = LocalContext.current as ComponentActivity

    AppNavGraph(navHostController = navigationState.navHostController,
        enterScreenContent = {},
        mainScreenContent = {
            val viewModel = ViewModelProvider(context, viewModelFactory)[MainViewModel::class.java]
            MainScreen(
                viewModel = viewModel,
                onTodoItemClick = {
                    navigationState.navigateToDetailScreen(it.id)
                },
                onAddButtonClick = {
                    navigationState.navigateToDetailScreen(TodoItem.UNDEFINED_ID)
                },
                onAddReserveTodoItem = { viewModel.addReserveTodoItem(it) },
                onDeleteClick = { id, isNetwork ->
                    viewModel.deleteTodoItem(id, isNetwork) },
                onDoneClick = { viewModel.doneTodoItem(it) },
                onRefreshTodoList = { viewModel.refreshTodoList() },
                isDark = isDark,
                changeTheme = changeTheme
            )
        },
        detailScreenContent = { id ->
            val viewModel = ViewModelProvider(context, viewModelFactory)[DetailViewModel::class.java]
            DetailScreen(
                id = id,
                viewModel = viewModel,
                onBackClickListener = { navigationState.navHostController.popBackStack() },
                onSaveClickListener = {
                    viewModel.saveTodoItem(it)
                    navigationState.navHostController.popBackStack()
                },
                onRefactorClickListener = {
                    viewModel.refactorTodoItem(it)
                    navigationState.navHostController.popBackStack()
                }
            )
        }
    )
}
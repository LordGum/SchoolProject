package com.example.schoolproject.presentation.detail.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.presentation.detail.DetailScreenState
import com.example.schoolproject.presentation.detail.DetailViewModel
import com.example.schoolproject.presentation.ui_elements.ErrorScreen
import com.example.schoolproject.presentation.ui_elements.LoadingScreen

@Composable
fun DetailScreen(
    id: String,
    viewModel: DetailViewModel,
    onBackClickListener: () -> Unit,
    onSaveClickListener: (TodoItem) -> Unit,
    onRefactorClickListener: (TodoItem) -> Unit
) {
    val screenState = viewModel.screenState.collectAsState(DetailScreenState.LoadingState)
    viewModel.getTodoItem(id)

    when (val currentState = screenState.value) {
        is DetailScreenState.RefactorTodoItemState -> {
            DetailScreenContent(
                onBackClickListener = onBackClickListener,
                onSaveClickListener = { onRefactorClickListener(it) },
                item = currentState.item,
                onDeleteClickListener = {
                    viewModel.deleteTodoItem(id)
                    onBackClickListener()
                }
            )
        }

        is DetailScreenState.AddTodoItemState -> {
            DetailScreenContent(
                onBackClickListener = onBackClickListener,
                onSaveClickListener = { onSaveClickListener(it) },
                item = currentState.item,
                onDeleteClickListener = {
                    viewModel.deleteTodoItem(id)
                    onBackClickListener()
                }
            )
        }

        is DetailScreenState.LoadingState -> {
            LoadingScreen()
        }

        is DetailScreenState.ErrorState -> {
            ErrorScreen()
        }
    }
}




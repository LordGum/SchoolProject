package com.example.schoolproject.presentation.main.ui.main_screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.schoolproject.domain.entities.TodoItem
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipedTodoListItem(
    item: TodoItem,
    onTodoItemClickListener: (TodoItem) -> Unit,
    onDoneClick: (TodoItem) -> Unit,
    onDeleteSwipe: () -> Unit,
    modifier: Modifier = Modifier
) {
    var show by remember { mutableStateOf(true) }
    val dismissState = rememberSwipeToDismissBoxState(
        initialValue = SwipeToDismissBoxValue.Settled,
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.Settled -> false
                SwipeToDismissBoxValue.StartToEnd -> false
                SwipeToDismissBoxValue.EndToStart -> {
                    show = false
                    true
                }
            }
        }
    )
    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier,
        enableDismissFromStartToEnd = false,
        backgroundContent = { DeleteBackground() },
        content = {
            Item(
                item = item,
                onDoneClick = onDoneClick,
                onTodoItemClickListener = onTodoItemClickListener
            )
        }
    )

    LaunchedEffect(show) {
        if (!show) {
            delay(200)
            onDeleteSwipe()
            show = true
        }
    }
}
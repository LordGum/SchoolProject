package com.example.schoolproject.presentation.main.ui.main_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.presentation.main.Item
import com.example.schoolproject.presentation.main.MainViewModel
import com.example.schoolproject.ui.theme.AppTheme
import com.example.schoolproject.ui.theme.Red

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun List(
    list: List<TodoItem>,
    viewModel: MainViewModel,
    onTodoItemClickListener: (TodoItem) -> Unit,
    visibilityState: Boolean
) {
    Card(
        modifier = Modifier.padding(top = 8.dp, bottom = 12.dp, start = 8.dp, end = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppTheme.colorScheme.backSecondary)
        ) {
            items(items = list, key = { it.id }) { item ->
                if (!item.isCompleted || visibilityState) {
                    val dismissState = rememberDismissState(
                        initialValue = DismissValue.Default
                    )
                    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                        viewModel.deleteTodoItem(item.id)
                    }
                    SwipeToDismiss(
                        modifier = Modifier
                            .animateItemPlacement()
                            .background(Red),
                        state = dismissState,
                        background = {
                            when (dismissState.targetValue) {
                                DismissValue.DismissedToStart -> DeleteBackground()
                                else -> {}
                            }
                        },
                        directions = setOf(
                            DismissDirection.EndToStart
                        ),
                        dismissContent = {
                            Item(
                                item = item,
                                viewModel = viewModel,
                                onTodoItemClickListener = onTodoItemClickListener
                            )
                        }
                    )
                }
            }
        }
    }
}
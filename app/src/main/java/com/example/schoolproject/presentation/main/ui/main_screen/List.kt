package com.example.schoolproject.presentation.main.ui.main_screen

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.ui.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun List(
    list: List<TodoItem>,
    onDeleteClick: (String) -> Unit,
    onDoneClick: (TodoItem) -> Unit,
    onTodoItemClick: (TodoItem) -> Unit,
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
                    SwipedTodoListItem(
                        item = item,
                        onTodoItemClickListener = onTodoItemClick,
                        onDeleteSwipe = { onDeleteClick(item.id) },
                        modifier = Modifier.animateItemPlacement(
                            animationSpec = tween(durationMillis = 0)
                        ),
                        onDoneClick = onDoneClick
                    )
                }
            }
        }
    }
}
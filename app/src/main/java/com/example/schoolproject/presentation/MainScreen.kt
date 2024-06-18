package com.example.schoolproject.presentation

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schoolproject.R
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.ui.theme.AppTheme
import com.example.schoolproject.ui.theme.Blue
import com.example.schoolproject.ui.theme.Green
import com.example.schoolproject.ui.theme.Red
import java.util.Date

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onTodoItemClick: (TodoItem) -> Unit
) {
    val screenState = viewModel.screenState.collectAsState(MainScreenState.Initial)

    when (val currentState = screenState.value) {
        is MainScreenState.TodoList -> {
            MainScreenContent(
                list = currentState.todoList,
                onTodoItemClickListener = onTodoItemClick,
                viewModel = viewModel
            )
        }
        is MainScreenState.Initial -> {
            InitialScreen(onTodoItemClickListener = onTodoItemClick)
        }
    }
}

@Composable
fun MainScreenContent(
    list: List<TodoItem>,
    onTodoItemClickListener: (TodoItem) -> Unit,
    viewModel: MainViewModel
) {
    Scaffold(
        containerColor = AppTheme.colorScheme.backPrimary,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onTodoItemClickListener(
                        TodoItem(
                            id = TodoItem.UNDEFINED_ID,
                            text = "",
                            priority = TodoItem.Priority.NORMAL,
                            isCompleted = false,
                            createdDate = Date()
                        )
                    )
                },
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(),
                containerColor = Blue
            ){
                Icon(
                    imageVector = Icons.Filled.Add,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    )  {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Title()
            UnderTitle()
            List(list, viewModel)
        }
    }
}

@Composable
fun InitialScreen(onTodoItemClickListener: (TodoItem) -> Unit) {
    Scaffold(
        containerColor = AppTheme.colorScheme.backPrimary,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onTodoItemClickListener(TodoItem(
                        id = TodoItem.UNDEFINED_ID,
                        text = "",
                        priority = TodoItem.Priority.NORMAL,
                            isCompleted = false,
                            createdDate = Date()
                        )
                    )
                },
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(),
                containerColor = Blue
            ){
                Icon(
                    imageVector = Icons.Filled.Add,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    )  {paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = stringResource(R.string.no_notes),
                fontSize = 16.sp,
                fontFamily = FontFamily.Default,
                color = AppTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun Title() {
    Box(modifier = Modifier
        .height(124.dp)
        .fillMaxWidth()
        .padding(start = 60.dp, top = 82.dp)
    ) {
        Text(
            text = stringResource(R.string.title),
            fontSize = 32.sp,
            fontFamily = FontFamily.Default,
            color = AppTheme.colorScheme.primary
        )
    }
}

@Composable
fun UnderTitle() {
    val visibility = remember {
        mutableStateOf(true)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .padding(start = 60.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(R.string.underlable),
                fontSize = 16.sp,
                fontFamily = FontFamily.Default,
                color = AppTheme.colorScheme.tertiary
            )
            Icon(
                painter = painterResource(
                    id = if (visibility.value) R.drawable.ic_visibility
                    else R.drawable.ic_visibility_off
                ),
                tint = Blue,
                contentDescription = stringResource(R.string.visibility_button_desc)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun List(
    list: List<TodoItem>,
    viewModel: MainViewModel
) {
    Card(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(items = list, key = { it.id }) { item ->
                val dismissState = rememberDismissState(
                    initialValue = DismissValue.Default
                )
                if (dismissState.isDismissed(DismissDirection.StartToEnd)) {
                    viewModel.doneTodoItem(item)
                }
                if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                    viewModel.deleteTodoItem(item.id)
                }
                SwipeToDismiss(
                    modifier = Modifier.animateItemPlacement(),
                    state = dismissState,
                    background = {
                        when(dismissState.targetValue) {
                            DismissValue.DismissedToEnd -> DoneBackground()
                            DismissValue.DismissedToStart -> DeleteBackground()
                            DismissValue.Default -> AppTheme.colorScheme.backPrimary
                        }
                    },
                    directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd),
                    dismissContent = {
                        Item(
                            item = item,
                            viewModel = viewModel
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun DeleteBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Red)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(R.string.delete_desc),
            tint = Color.White
        )
    }
}

@Composable
fun DoneBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Green)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Icon(
            imageVector = Icons.Default.Done,
            contentDescription = stringResource(R.string.delete_desc),
            tint = Color.White
        )
    }
}
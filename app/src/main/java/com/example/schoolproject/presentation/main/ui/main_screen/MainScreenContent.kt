package com.example.schoolproject.presentation.main.ui.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.presentation.ui_elements.PreviewData
import com.example.schoolproject.ui.theme.AppTheme
import com.example.schoolproject.ui.theme.AppThemePreview
import com.example.schoolproject.ui.theme.Blue
import com.example.schoolproject.ui.theme.SchoolProjectTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(
    list: List<TodoItem>,
    onTodoItemClick: (TodoItem) -> Unit,
    onAddButtonClick: () -> Unit,
    onDeleteClick: (String) -> Unit,
    onDoneClick: (TodoItem) -> Unit,
    countDone: Int,
    onVisibilityIconClick: () -> Unit,
    visibilityState: Boolean
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val listState = rememberLazyListState()
    val isTopScroll = remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }
    val needToScroll = remember { mutableStateOf(false) }

    LaunchedEffect(needToScroll.value && !isTopScroll.value) {
        launch { listState.animateScrollToItem(index = 0) }
    }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                doneTasks = countDone,
                visibilityState = visibilityState,
                onVisibilityIconClick = {
                    onVisibilityIconClick()
                    if (isTopScroll.value) { needToScroll.value = true }
                }
            )
        },
        containerColor = AppTheme.colorScheme.backPrimary,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddButtonClick() },
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
    )  {

        Column(modifier = Modifier.padding(it)) {
            List(
                list = list,
                onDeleteClick = onDeleteClick,
                onDoneClick = onDoneClick,
                onTodoItemClick = onTodoItemClick,
                visibilityState = visibilityState
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640, locale = "ru")
@Composable
fun PreviewMainScreen(
    @PreviewParameter(AppThemePreview::class) isDarkTheme: Boolean
) {
    SchoolProjectTheme(isDarkTheme) {
        MainScreenContent(
            list = PreviewData.getPreviewData(),
            onTodoItemClick = {},
            onAddButtonClick = {},
            onDoneClick = {},
            onDeleteClick = {},
            onVisibilityIconClick = {},
            visibilityState = true,
            countDone = 111
        )
    }
}
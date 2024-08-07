package com.example.schoolproject.presentation.main.ui.main_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.example.schoolproject.R
import com.example.schoolproject.domain.entities.ErrorState
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.presentation.ui_elements.PreviewData
import com.example.schoolproject.ui.theme.AppTheme
import com.example.schoolproject.ui.theme.AppThemePreview
import com.example.schoolproject.ui.theme.Blue
import com.example.schoolproject.ui.theme.SchoolProjectTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.internal.format

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(
    list: List<TodoItem>,
    onTodoItemClick: (TodoItem) -> Unit,
    onAddButtonClick: () -> Unit,
    onDeleteClick: (String, Boolean) -> Unit,
    onAddReserveTodoItem: (TodoItem) -> Unit,
    onDoneClick: (TodoItem) -> Unit,
    onRefreshTodoList: () -> Deferred<Unit>,
    onVisibilityIconClick: () -> Unit,
    visibilityState: Boolean,
    countDone: Int,
    errorState: ErrorState?,
    isDark: Boolean,
    onChangeTheme: (Boolean) -> Unit
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val pullToRefreshState = rememberPullToRefreshState(
        enabled = { scrollBehavior.state.collapsedFraction == 0f }
    )
    val listState = rememberLazyListState()
    val isTopScroll = remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }
    val scroll = remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(scroll.value && !isTopScroll.value) {
        launch { listState.animateScrollToItem(index = 0) }
    }

    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    LaunchedEffect(errorState) {
        errorState?.let {
            launch {
                val errorText = context.getString(errorState.toStringResource())
                snackBarHostState.showSnackbar(errorText)
            }
        }
    }

    val todoItemKey = rememberSaveable { mutableStateOf<TodoItem?>(null) }
    val dismissKey = rememberSaveable { mutableStateOf<Int?>(null) }
    val text = stringResource(id = R.string.cancel_snackbar_name)
    val cancelButton = stringResource(id = R.string.cancel)
    LaunchedEffect(dismissKey.value) {
        todoItemKey.value?.let { todoItem ->
            launch {
                val result = snackBarHostState.showSnackbar(
                    message = format(text, todoItem.text),
                    actionLabel = cancelButton,
                    duration = SnackbarDuration.Short
                )
                if (result == SnackbarResult.ActionPerformed) {
                    onAddReserveTodoItem(todoItem)
                } else {
                    onDeleteClick(todoItem.id, true)
                    todoItemKey.value = null
                }
            }
        }
    }


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState
            ) { data ->
                CustomSnackBar(data)

            }
        },
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                doneTasks = countDone,
                visibilityState = visibilityState,
                onVisibilityIconClick = {
                    onVisibilityIconClick()
                    if (isTopScroll.value) {
                        scroll.value = true
                    }
                }
            )
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (pullToRefreshState.isRefreshing) {
                    LaunchedEffect(true) {
                        scope.launch {
                            pullToRefreshState.startRefresh()
                            onRefreshTodoList().await()
                            pullToRefreshState.endRefresh()
                        }
                    }
                }

                PullToRefreshContainer(
                    state = pullToRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    containerColor = AppTheme.colorScheme.backPrimary,
                    contentColor = Blue
                )
            }
        },
        containerColor = AppTheme.colorScheme.backPrimary,
        floatingActionButton = {
            FAB(
                isDark = isDark,
                onAddButtonClick = {
                    onAddButtonClick()
                    todoItemKey.value = null
                    dismissKey.value = null
                },
                onThemeChange = onChangeTheme
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .nestedScroll(pullToRefreshState.nestedScrollConnection)
        ) {
            List(
                list = list,
                onDeleteClick = { todoItem ->
                    todoItemKey.value = todoItem
                    dismissKey.value = (dismissKey.value ?: 0) + 1
                    onDeleteClick(todoItem.id, false)
                },
                onDoneClick = onDoneClick,
                onTodoItemClick = { todoItem ->
                    onTodoItemClick(todoItem)
                    todoItemKey.value = null
                    dismissKey.value = null
                },
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
            onAddReserveTodoItem = {},
            onDeleteClick = { _, _ -> },
            onVisibilityIconClick = {},
            visibilityState = true,
            countDone = 111,
            onRefreshTodoList = { CoroutineScope(Dispatchers.Main).async { } },
            errorState = null,
            isDark = isDarkTheme,
            onChangeTheme = {}
        )
    }
}

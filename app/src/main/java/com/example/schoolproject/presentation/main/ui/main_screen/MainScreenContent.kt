package com.example.schoolproject.presentation.main.ui.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.presentation.main.MainViewModel
import com.example.schoolproject.presentation.main.toolbar.CollapsingTitle
import com.example.schoolproject.presentation.main.toolbar.CustomToolbar
import com.example.schoolproject.presentation.main.toolbar.rememberToolbarScrollBehavior
import com.example.schoolproject.ui.theme.AppTheme
import com.example.schoolproject.ui.theme.Blue

@Composable
fun MainScreenContent(
    list: List<TodoItem>,
    onTodoItemClickListener: (TodoItem) -> Unit,
    onAddButtonClick: () -> Unit,
    viewModel: MainViewModel,
    countDone: Int,
    onVisibilityIconClick: () -> Unit,
    visibilityState: Boolean
) {
    val scrollBehavior = rememberToolbarScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomToolbar(
                modifier = Modifier.background(AppTheme.colorScheme.backPrimary),
                collapsingTitle = CollapsingTitle.large(titleText = "Мои дела"),
                scrollBehavior = scrollBehavior
            )
        },
        containerColor = AppTheme.colorScheme.backPrimary,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddButtonClick()
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
    )  {
        Column (
            modifier = Modifier.padding(it)
        ) {
            UnderTitle(countDone, onVisibilityIconClick, visibilityState)
            List(list, viewModel, onTodoItemClickListener, visibilityState)
        }
    }
}
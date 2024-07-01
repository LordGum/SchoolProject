package com.example.schoolproject.ui.theme.detail_screen

import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schoolproject.R
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.presentation.detail.DetailComment
import com.example.schoolproject.ui.theme.AppTheme
import com.example.schoolproject.ui.theme.Blue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenContent(
    item: TodoItem,
    isCalendar: Boolean
) {
    val currentItem = rememberSaveable { mutableStateOf(item) }
    val errorState = rememberSaveable{ mutableStateOf(false) }
    val scrollState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .background(AppTheme.colorScheme.backPrimary),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(R.string.close_description)
                        )
                    }
                },
                actions = {
                    TextButton(onClick = {}) {
                        Text(
                            text = stringResource(R.string.save),
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Default,
                            color = Blue
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                modifier = Modifier
                    .shadow(elevation = if (scrollState.canScrollBackward) 8.dp else 0.dp),
                colors = TopAppBarDefaults.topAppBarColors(AppTheme.colorScheme.backPrimary)
            )
        },
        content = { paddingValues ->
            Box (
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(AppTheme.colorScheme.backPrimary)
            ){
                Column {
                    DetailComment(
                        item = currentItem.value,
                        errorState = errorState.value
                    )
                    DetailColumn(
                        item = currentItem.value,
                        isCalendarMenu = isCalendar
                    )
                }
            }
        }
    )
}




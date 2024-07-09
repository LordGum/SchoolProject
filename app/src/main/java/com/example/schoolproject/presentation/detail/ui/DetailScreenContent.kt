package com.example.schoolproject.presentation.detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schoolproject.R
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.presentation.ui_elements.PreviewData
import com.example.schoolproject.ui.theme.AppTheme
import com.example.schoolproject.ui.theme.AppThemePreview
import com.example.schoolproject.ui.theme.Blue
import com.example.schoolproject.ui.theme.SchoolProjectTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenContent(
    onBackClickListener: () -> Unit,
    onSaveClickListener: (TodoItem) -> Unit,
    onDeleteClickListener: () -> Unit,
    item: TodoItem
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
                    IconButton(onClick = { onBackClickListener() }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            tint = AppTheme.colorScheme.primary,
                            contentDescription = stringResource(R.string.close_description)
                        )
                    }
                },
                actions = {
                    TextButton(onClick = {
                        if (currentItem.value.text.trim().isNotBlank()) onSaveClickListener(currentItem.value)
                        else errorState.value = true
                    }) {
                        Text(
                            text = stringResource(R.string.save),
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Default,
                            color = Blue
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                modifier = Modifier.shadow(elevation = if (scrollState.canScrollBackward) 8.dp else 0.dp),
                colors = TopAppBarDefaults.topAppBarColors(AppTheme.colorScheme.backPrimary)
            )
        },
        content = { paddingValues ->
            Box (
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(AppTheme.colorScheme.backPrimary)
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ){
                Column {
                    DetailComment(
                        onItemChange = { newItem ->
                            currentItem.value = newItem
                            if (newItem.text.isNotBlank()) errorState.value = false
                        },
                        item = currentItem.value,
                        errorState = errorState.value
                    )
                    DetailColumn(
                        item = currentItem.value,
                        onItemChange = { newItem -> currentItem.value = newItem },
                        onDeleteIconClickListener = onDeleteClickListener
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640, locale = "ru")
@Composable
fun PreviewMainScreen(
    @PreviewParameter(AppThemePreview::class) isDarkTheme: Boolean
) {
    SchoolProjectTheme(isDarkTheme) {
        DetailScreenContent(
            onBackClickListener = {},
            onSaveClickListener = {},
            onDeleteClickListener = {},
            item = PreviewData.getPreviewTodoItem()
        )
    }
}

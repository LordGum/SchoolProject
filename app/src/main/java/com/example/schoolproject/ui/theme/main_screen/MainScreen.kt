package com.example.schoolproject.ui.theme.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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


@Composable
fun MainScreenTheme(
    list: List<TodoItem>,
    countDone: Int,
    visibilityState: Boolean
) {
    Scaffold(
        containerColor = AppTheme.colorScheme.backPrimary,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
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
            Title()
            UnderTitle(countDone, visibilityState)
            List(list, visibilityState)
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
fun UnderTitle(
    count: Int,
    visibilityState: Boolean
) {
    val text = stringResource(R.string.underlable)
    val finalText = String.format(text, count)
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
                text = finalText,
                fontSize = 16.sp,
                fontFamily = FontFamily.Default,
                color = AppTheme.colorScheme.tertiary
            )
            Icon(
                painter = painterResource(
                    id = if (visibilityState) R.drawable.ic_visibility
                    else R.drawable.ic_visibility_off
                ),
                tint = Blue,
                contentDescription = stringResource(R.string.visibility_button_desc),
            )
        }
    }
}

@Composable
fun List(
    list: List<TodoItem>,
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
                if (visibilityState || !item.isCompleted) ItemScreen(item = item)
            }
        }
    }
}
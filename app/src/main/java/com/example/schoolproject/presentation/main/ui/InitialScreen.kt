package com.example.schoolproject.presentation.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schoolproject.R
import com.example.schoolproject.domain.entities.ErrorState
import com.example.schoolproject.presentation.main.ui.main_screen.Title
import com.example.schoolproject.ui.theme.AppTheme
import com.example.schoolproject.ui.theme.Blue
import kotlinx.coroutines.launch


@Composable
fun InitialScreen(
    onAddButtonClick: () -> Unit,
    errorState: ErrorState?
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        containerColor = AppTheme.colorScheme.backPrimary,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddButtonClick()
                },
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(),
                containerColor = Blue
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Title()
            Card(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 12.dp, start = 8.dp, end = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = AppTheme.colorScheme.backSecondary),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
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

        errorState?.let {
            val errorText = stringResource(errorState.toStringResource())
            scope.launch { snackBarHostState.showSnackbar(errorText) }
        }
    }
}

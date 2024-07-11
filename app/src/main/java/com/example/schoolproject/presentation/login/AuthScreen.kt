package com.example.schoolproject.presentation.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.schoolproject.R
import com.example.schoolproject.ui.theme.Blue

@Composable
fun AuthScreen(
    onAuthClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { onAuthClick() },
            colors = ButtonColors(
                containerColor = Blue,
                contentColor = Color.White,
                disabledContainerColor = Blue,
                disabledContentColor = Color.White
            )
        ) {
            Text(text = stringResource(R.string.enter_button))
        }
    }
}
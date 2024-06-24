package com.example.schoolproject.ui.theme.detail_screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schoolproject.R
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.ui.theme.AppTheme

@Composable
fun DetailComment(
    item: TodoItem,
    errorState: Boolean
) {
    val textState = rememberSaveable { mutableStateOf(item.text) }

    TextField(
        value = textState.value,
        onValueChange = { newText ->
            textState.value = newText
        },
        placeholder = {
            Text(
                text = stringResource(R.string.place_holder),
                fontSize = 16.sp,
                fontFamily = FontFamily.Default,
                color = AppTheme.colorScheme.tertiary
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(4.dp),
        shape = RoundedCornerShape(8.dp),
        minLines = 8,
        isError = errorState,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = AppTheme.colorScheme.backSecondary,
            unfocusedContainerColor = AppTheme.colorScheme.backSecondary,
            disabledContainerColor = AppTheme.colorScheme.backSecondary,
            focusedTextColor = AppTheme.colorScheme.primary,
            unfocusedTextColor = AppTheme.colorScheme.tertiary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}
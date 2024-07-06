package com.example.schoolproject.presentation.main.ui.main_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schoolproject.R
import com.example.schoolproject.ui.theme.AppTheme
import com.example.schoolproject.ui.theme.Blue

@Composable
fun UnderTitle(
    count: Int,
    onVisibilityIconClick: () -> Unit,
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
                modifier = Modifier.clickable {
                    onVisibilityIconClick()
                }
            )
        }
    }
}
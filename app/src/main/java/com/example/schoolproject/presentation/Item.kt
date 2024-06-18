package com.example.schoolproject.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schoolproject.R
import com.example.schoolproject.ui.theme.AppTheme

@Preview
@Composable
fun Item() {
    val checked = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .background(AppTheme.colorScheme.backSecondary)
            .padding(12.dp, 16.dp, 12.dp, 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomCheckbox(
            checked = checked.value, isHigh = false, onClick = {
                checked.value = it
            }
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            modifier = Modifier.weight(10f),
            text = "Купи, что нибудь",
            style = TextStyle(
                textDecoration = TextDecoration.LineThrough,
                fontFamily = FontFamily.Default,
                fontSize = 16.sp,
                color = if (checked.value) AppTheme.colorScheme.tertiary
                        else AppTheme.colorScheme.primary
            )
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_info),
            contentDescription = stringResource(R.string.info_button_description),
            tint = AppTheme.colorScheme.separator,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun CustomCheckbox(
    checked: Boolean,
    isHigh: Boolean,
    onClick: (Boolean) -> Unit
) {
    Box() {
        Box(
            modifier = Modifier
                .size(24.dp)
                .border(
                    BorderStroke(
                        1.dp, color =
                        if (isHigh && !checked) Color.Red else AppTheme.colorScheme.separator
                    ),
                    shape = RoundedCornerShape(3.dp)
                )
                .background(
                    color = if (checked) Color.Green else {
                        if (isHigh) Color.Red.copy(alpha = 0.15f)
                        else Color.White
                    },
                    shape = RoundedCornerShape(3.dp)
                )
                .alpha(15f)
                .clickable {
                    onClick(!checked)
                }
        ) {
            if (checked) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                )
            }
        }
    }
}

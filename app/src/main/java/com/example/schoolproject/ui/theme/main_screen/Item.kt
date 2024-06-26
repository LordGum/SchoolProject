package com.example.schoolproject.ui.theme.main_screen


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schoolproject.R
import com.example.schoolproject.domain.entities.TodoItem
import com.example.schoolproject.ui.theme.AppTheme
import com.example.schoolproject.ui.theme.Green
import java.text.SimpleDateFormat


@Composable
fun ItemScreen(item: TodoItem) {
    val checked = item.isCompleted

    Column {
        Row(
            modifier = Modifier
                .background(AppTheme.colorScheme.backSecondary)
                .padding(12.dp, 16.dp, 12.dp, 16.dp)
                .fillMaxWidth()
        ) {
            CustomCheckboxScreen( item = item)
            Spacer(modifier = Modifier.width(12.dp))
            Box(modifier = Modifier.weight(10f)) {
                Column {
                    BasicText(
                        text = item.text,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            textDecoration = if (checked) TextDecoration.LineThrough
                            else TextDecoration.None,
                            fontFamily = FontFamily.Default,
                            fontSize = 16.sp,
                            color = if (checked) AppTheme.colorScheme.tertiary
                            else AppTheme.colorScheme.primary
                        )
                    )
                    if (item.deadline != null && !checked) {
                        val text = stringResource(R.string.deadline_date)
                        val formatter = SimpleDateFormat("dd MMMM yyyy")
                        val date = formatter.format(item.deadline)
                        val finalText = String.format(text, date)

                        Text(
                            modifier = Modifier
                                .background(AppTheme.colorScheme.backSecondary)
                                .fillMaxWidth(),
                            text = finalText,
                            style = TextStyle(
                                fontFamily = FontFamily.Default,
                                fontSize = 16.sp,
                                color = AppTheme.colorScheme.tertiary
                            )
                        )
                    }
                }
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_info),
                contentDescription = stringResource(R.string.info_button_description),
                tint = AppTheme.colorScheme.separator,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun CustomCheckboxScreen(item: TodoItem) {
    val checked = item.isCompleted
    val isHigh = item.priority == TodoItem.Priority.HIGH
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
                color = if (checked) Green else {
                    if (isHigh) Color.Red.copy(alpha = 0.15f)
                    else AppTheme.colorScheme.backSecondary
                },
                shape = RoundedCornerShape(3.dp)
            )
            .alpha(15f)
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
package com.example.schoolproject.presentation.main.ui.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.schoolproject.R
import com.example.schoolproject.ui.theme.Blue
import kotlinx.coroutines.delay


@Composable
fun CustomSnackBar(
    snackBarData: SnackbarData
) {

    var visible by remember { mutableStateOf(true) }
    LaunchedEffect(snackBarData) {
        visible = false
        delay(5000)
        snackBarData.dismiss()
    }

    val context = LocalContext.current
    val actionLabel = snackBarData.visuals.actionLabel

    val actionComposable: (@Composable () -> Unit)? = if (actionLabel != null) {
        @Composable {
            TextButton(
                modifier = Modifier.semantics {
                    contentDescription = context.getString(R.string.cancel_delete)
                },
                colors = ButtonDefaults.textButtonColors(contentColor = Blue),
                onClick = { snackBarData.performAction() },
                content = { Text(actionLabel) }
            )
        }
    } else {
        null
    }

    Snackbar(
        modifier = Modifier.padding(10.dp),
        action = actionComposable
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier.weight(11f),
                text = snackBarData.visuals.message,
                maxLines = 2,
                textAlign = TextAlign.Center
            )
        }
    }
}

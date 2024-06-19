import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.schoolproject.ui.theme.Blue

@Composable
fun CustomIndeterminateCircularProgressIndicator(
    modifier: Modifier = Modifier,
    strokeWidth: Float = 16f,
    color: Color = Blue
) {
    val transition = rememberInfiniteTransition(label = "")
    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Canvas(modifier = modifier.size(48.dp)) {
        drawIndeterminateCircularIndicator(
            color,
            strokeWidth = strokeWidth,
            rotationDegrees = rotation
        )
    }
}

private fun DrawScope.drawIndeterminateCircularIndicator(
    color: Color,
    strokeWidth: Float,
    rotationDegrees: Float
) {
    val startAngle = 0f + rotationDegrees
    val sweepAngle = 220f

    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(width = strokeWidth)
    )
}

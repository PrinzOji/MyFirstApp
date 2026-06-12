package com.Ojiem.myfirstapp.ui.theme

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

// Core Colors
val HubCyan = Color(0xFF00E5FF)
val HubDeepBlue = Color(0xFF000428)
val HubNavy = Color(0xFF004E92)

val ScreenGradientColors = listOf(HubDeepBlue, HubNavy, HubDeepBlue)
val ButtonGradientColors = listOf(Color(0xFF2196F3), Color(0xFF7B1FA2))

@Composable
fun shiftingGradient(colors: List<Color>, durationMillis: Int = 8000): Brush {
    val infiniteTransition = rememberInfiniteTransition(label = "gradient")
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "progress",
    )

    val animatedColors = colors.mapIndexed { index, color ->
        val targetIndex = (index + 1) % colors.size
        infiniteTransition.animateColor(
            initialValue = color,
            targetValue = colors[targetIndex],
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse,
            ),
            label = "color_$index",
        ).value
    }

    return Brush.linearGradient(
        colors = animatedColors,
        start = Offset(progress * 1000f, 0f),
        end = Offset((1f - progress) * 1000f, 1000f),
        tileMode = TileMode.Mirror,
    )
}

@Composable
fun Modifier.glassmorphism(
    shape: Shape,
    containerColor: Color = Color.White.copy(alpha = 0.05f),
    borderColor: Color = Color.White.copy(alpha = 0.1f)
) = this
    .background(containerColor, shape)
    .border(1.dp, borderColor, shape)

@Composable
fun TheHubBackground(content: @Composable () -> Unit) {
    val brush = shiftingGradient(ScreenGradientColors)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush),
    ) {
        RotatingGlobeAnimation()
        ScanningLineAnimation()
        content()
    }
}

@Composable
fun ScanningLineAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "scan")
    val yPos by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "yPos",
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val currentY = yPos * size.height
        drawLine(
            brush = Brush.verticalGradient(
                colors = listOf(Color.Transparent, HubCyan.copy(alpha = 0.1f), Color.Transparent)
            ),
            start = Offset(0f, currentY),
            end = Offset(size.width, currentY),
            strokeWidth = 20.dp.toPx()
        )
    }
}

@Composable
fun RotatingGlobeAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "globe")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "rotation",
    )
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "pulse",
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val globeRadius = minOf(size.width, size.height) * 0.4f

        // Longitude
        for (i in 0 until 12) {
            val angleRad = (rotation + i * 30) * (PI / 180).toFloat()
            val ellipseWidth = cos(angleRad) * globeRadius * 2
            if (ellipseWidth > 0) {
                drawOval(
                    color = HubCyan.copy(alpha = 0.3f * (ellipseWidth / (globeRadius * 2))),
                    topLeft = Offset(centerX - ellipseWidth / 2, centerY - globeRadius),
                    size = androidx.compose.ui.geometry.Size(ellipseWidth.toFloat(), globeRadius * 2),
                    style = Stroke(width = 1.dp.toPx()),
                )
            }
        }

        // Latitude
        for (i in -4..4) {
            val yOffset = i * globeRadius / 5
            val r = kotlin.math.sqrt(globeRadius * globeRadius - yOffset * yOffset)
            drawOval(
                color = HubCyan.copy(alpha = 0.15f),
                topLeft = Offset(centerX - r, centerY + yOffset - (r * 0.1f)),
                size = androidx.compose.ui.geometry.Size(r * 2, r * 0.2f),
                style = Stroke(width = 1.dp.toPx()),
            )
        }
    }
}

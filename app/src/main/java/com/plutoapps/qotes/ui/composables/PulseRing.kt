package com.plutoapps.qotes.ui.composables

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PulseRing(isSecondary : Boolean = true, isSmall:Boolean = true ) {
    val infiniteTransition = rememberInfiniteTransition(label = "it")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(tween(20_000), RepeatMode.Reverse), label = "ScaleRing"
    )
    val color by infiniteTransition.animateColor(
        initialValue = MaterialTheme.colorScheme.background,
        targetValue = if (isSecondary) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.tertiaryContainer,
        animationSpec = infiniteRepeatable(tween(20_000),RepeatMode.Reverse), label = "ColorRing"
    )

    Canvas(
        modifier = Modifier
            .size(if(isSmall) 64.dp else 128.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                transformOrigin = TransformOrigin.Center
            } // Set desired size
    ) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val radius = if(isSmall) 20.dp.toPx() else 40.dp.toPx() // Adjust radius as needed
        val innerRadius = if(isSmall) 20.dp.toPx() else 10.dp.toPx() // Adjust inner radius as needed

        drawCircle(
            color = color,
            center = Offset(centerX, centerY),
            radius = radius,
            style = Stroke(
                width = 2.dp.toPx(), // Adjust stroke width as needed
                cap = StrokeCap.Round
            )
        )

        drawCircle(
            color = Color.Transparent,
            center = Offset(centerX, centerY),
            radius = innerRadius
        )
    }

}

@Preview
@Composable
fun PulseRingPreview() {
    PulseRing()
}
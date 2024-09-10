package com.personal.tmdb.core.util

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.offset

@Composable
fun Modifier.shimmerEffect(
    primaryColor: Color = MaterialTheme.colorScheme.surfaceContainerLow,
    secondaryColor: Color = MaterialTheme.colorScheme.surfaceContainer
): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "Transition")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(tween(1500)), label = "Transition"
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(secondaryColor, primaryColor, secondaryColor),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}

/*TODO: Make fadeEdge modifier*/

fun Modifier.negativeHorizontalPadding(
    padding: Dp
): Modifier = this.then(
    Modifier.layout { measurable, constraints ->
        val placeable = measurable.measure(constraints.offset(horizontal = -padding.roundToPx() * 2))
        layout(
            width = placeable.width + padding.roundToPx() * 2,
            height = placeable.height
        ) {
            placeable.place(+padding.roundToPx(), 0)
        }
    }
)
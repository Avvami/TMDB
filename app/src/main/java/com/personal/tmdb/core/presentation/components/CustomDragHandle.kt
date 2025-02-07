package com.personal.tmdb.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal object DragHandleTokens {
    val DockedDragHandleHeight = 4.0.dp
    val DockedDragHandleWidth = 32.0.dp
    val DragHandleVerticalPadding = 16.dp
}

/** The optional visual marker placed on top of a bottom sheet to indicate it may be dragged. */
@Composable
fun CustomDragHandle(
    modifier: Modifier = Modifier,
    width: Dp = DragHandleTokens.DockedDragHandleWidth,
    height: Dp = DragHandleTokens.DockedDragHandleHeight,
    shape: Shape = MaterialTheme.shapes.extraLarge,
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
) {
    val dragHandleDescription = "BottomSheetDragHandle"
    Surface(
        modifier = modifier
            .padding(vertical = DragHandleTokens.DragHandleVerticalPadding)
            .semantics { contentDescription = dragHandleDescription },
        color = color.copy(alpha = .4f),
        shape = shape
    ) {
        Box(modifier = Modifier.size(width = width, height = height))
    }
}
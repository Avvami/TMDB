package com.personal.tmdb.core.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomListItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    headlineContent: @Composable () -> Unit,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    selected: Boolean = false,
    enabled: Boolean = true,
    colors: ListItemColors = ListItemDefaults.colors(
        leadingIconColor = MaterialTheme.colorScheme.surfaceVariant,
        headlineColor = MaterialTheme.colorScheme.onSurface,
        trailingIconColor = MaterialTheme.colorScheme.surfaceVariant
    )
) {
    val containerColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.onSurface.copy(alpha = .05f) else Color.Transparent,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "Container Color"
    )

    Surface(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        color = containerColor
    ) {
        Row(
            modifier = Modifier.padding(contentPadding),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingContent?.let {
                CompositionLocalProvider(
                    LocalContentColor provides colors.leadingIconColor,
                    content = it
                )
            }
            ProvideContentColorTextStyle(
                contentColor = colors.headlineColor,
                textStyle = MaterialTheme.typography.bodyLarge
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    headlineContent()
                }
            }
            trailingContent?.let {
                ProvideContentColorTextStyle(
                    contentColor = colors.trailingIconColor,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    content = it
                )
            }
        }
    }
}
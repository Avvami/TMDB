package com.personal.tmdb.detail.presentation.detail.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import com.personal.tmdb.core.domain.util.shimmerEffect

@Composable
fun DetailOverview(
    modifier: Modifier = Modifier,
    overview: String
) {
    Text(
        modifier = modifier,
        text = overview,
        style = MaterialTheme.typography.bodyLarge,
        maxLines = 4,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun DetailOverviewShimmer() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.extraSmall)
            .shimmerEffect(),
        text = "",
        style = MaterialTheme.typography.bodyLarge,
        minLines = 4
    )
}
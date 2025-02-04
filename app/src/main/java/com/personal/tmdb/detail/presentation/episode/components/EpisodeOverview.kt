package com.personal.tmdb.detail.presentation.episode.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun EpisodeOverview(
    modifier: Modifier = Modifier,
    overview: String
) {
    var collapsed by rememberSaveable { mutableStateOf(true) }
    Text(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                collapsed = !collapsed
            },
        text = overview,
        style = MaterialTheme.typography.bodyLarge,
        maxLines = if (collapsed) 4 else Int.MAX_VALUE,
        overflow = TextOverflow.Ellipsis
    )
}
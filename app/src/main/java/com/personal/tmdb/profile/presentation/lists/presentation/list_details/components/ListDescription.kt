package com.personal.tmdb.profile.presentation.lists.presentation.list_details.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.personal.tmdb.core.domain.util.UiText

@Composable
fun ListDescription(
    modifier: Modifier = Modifier,
    description: UiText
) {
    var collapsed by rememberSaveable { mutableStateOf(true) }
    Text(
        modifier = modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
            .animateContentSize()
            .clickable(
                interactionSource = null,
                indication = null
            ) {
                collapsed = !collapsed
            },
        text = description.asString(),
        style = MaterialTheme.typography.bodyLarge,
        maxLines = if (collapsed) 4 else Int.MAX_VALUE,
        overflow = TextOverflow.Ellipsis
    )
}
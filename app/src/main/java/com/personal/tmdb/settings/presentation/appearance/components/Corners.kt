package com.personal.tmdb.settings.presentation.appearance.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.UiEvent
import com.personal.tmdb.core.presentation.PreferencesState

@Composable
fun Corners(
    preferencesState: State<PreferencesState>,
    uiEvent: (UiEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stringResource(id = R.string.corners),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Slider(
                modifier = Modifier.weight(1f),
                value = preferencesState.value.corners.toFloat(),
                onValueChange = { uiEvent(UiEvent.SetCorners(it.toInt())) },
                steps = 24,
                valueRange = 6f..24f
            )
            Text(
                modifier = Modifier.weight(.15f),
                text = preferencesState.value.corners.toString(),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    }
}
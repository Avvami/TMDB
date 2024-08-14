package com.personal.tmdb.settings.presentation.appearance.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.UiEvent
import com.personal.tmdb.core.presentation.PreferencesState

@Composable
fun ShowAdditionalInfo(
    preferencesState: State<PreferencesState>,
    uiEvent: (UiEvent) -> Unit,
) {
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerLow)
    ) {
        AnimatedVisibility(visible = !preferencesState.value.useCards) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { uiEvent(UiEvent.SetShowTitle(!preferencesState.value.showTitle)) }
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_title_fill0_wght400),
                        contentDescription = stringResource(id = R.string.add_title),
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = stringResource(id = R.string.add_title),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Switch(
                    checked = preferencesState.value.showTitle,
                    onCheckedChange = { uiEvent(UiEvent.SetShowTitle(!preferencesState.value.showTitle)) }
                )
            }
            HorizontalDivider(
                modifier = Modifier.padding(start = 52.dp),
                color = MaterialTheme.colorScheme.background
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { uiEvent(UiEvent.SetShowVoteAverage(!preferencesState.value.showVoteAverage)) }
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_bar_chart_fill0_wght400),
                    contentDescription = stringResource(id = R.string.show_user_score),
                    tint = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = stringResource(id = R.string.show_user_score),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Switch(
                checked = preferencesState.value.showVoteAverage,
                onCheckedChange = { uiEvent(UiEvent.SetShowVoteAverage(!preferencesState.value.showVoteAverage)) }
            )
        }
    }
}
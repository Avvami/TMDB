package com.personal.tmdb.settings.presentation.appearance.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.CustomListItem
import com.personal.tmdb.settings.presentation.appearance.AppearanceUiEvent

@Composable
fun AppearancePoster(
    modifier: Modifier = Modifier,
    preferencesState: () -> PreferencesState,
    appearanceUiEvent: (AppearanceUiEvent) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.posters),
            style = MaterialTheme.typography.titleMedium
        )
        Column {
            CustomListItem(
                onClick = {
                    appearanceUiEvent(AppearanceUiEvent.SetShowTitle(!preferencesState().showTitle))
                },
                headlineContent = {
                    Text(text = stringResource(id = R.string.show_title))
                },
                leadingContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_title_fill0_wght400),
                        contentDescription = stringResource(id = R.string.show_title)
                    )
                },
                trailingContent = {
                    CompositionLocalProvider(
                        LocalMinimumInteractiveComponentSize provides Dp.Unspecified
                    ) {
                        Switch(
                            checked = preferencesState().showTitle,
                            onCheckedChange = { appearanceUiEvent(AppearanceUiEvent.SetShowTitle(!preferencesState().showTitle)) },
                            colors = SwitchDefaults.colors(
                                uncheckedThumbColor = MaterialTheme.colorScheme.surfaceVariant,
                                uncheckedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                                uncheckedTrackColor = MaterialTheme.colorScheme.surface
                            )
                        )
                    }
                }
            )
            CustomListItem(
                onClick = {
                    appearanceUiEvent(AppearanceUiEvent.SetShowVoteAverage(!preferencesState().showVoteAverage))
                },
                headlineContent = {
                    Text(text = stringResource(id = R.string.show_user_score))
                },
                leadingContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_thumbs_up_down_fill0_wght400),
                        contentDescription = stringResource(id = R.string.show_user_score)
                    )
                },
                trailingContent = {
                    CompositionLocalProvider(
                        LocalMinimumInteractiveComponentSize provides Dp.Unspecified
                    ) {
                        Switch(
                            checked = preferencesState().showVoteAverage,
                            onCheckedChange = { appearanceUiEvent(AppearanceUiEvent.SetShowVoteAverage(!preferencesState().showVoteAverage)) },
                            colors = SwitchDefaults.colors(
                                uncheckedThumbColor = MaterialTheme.colorScheme.surfaceVariant,
                                uncheckedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                                uncheckedTrackColor = MaterialTheme.colorScheme.surface
                            )
                        )
                    }
                }
            )
        }
    }
}
package com.personal.tmdb.settings.presentation.appearance.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.HorizontalSegmentedButton
import com.personal.tmdb.core.presentation.components.SegmentedButtonsRow
import com.personal.tmdb.settings.presentation.appearance.AppearanceUiEvent

@Composable
fun AppearanceTheme(
    modifier: Modifier = Modifier,
    preferencesState: () -> PreferencesState,
    appearanceUiEvent: (AppearanceUiEvent) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.theme),
            style = MaterialTheme.typography.titleMedium
        )
        SegmentedButtonsRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            HorizontalSegmentedButton(
                onClick = { appearanceUiEvent(AppearanceUiEvent.SetTheme(false)) },
                selected = preferencesState().darkTheme == false,
                shape = MaterialTheme.shapes.medium,
                icon = {
                    Icon(
                        modifier = Modifier.size(SegmentedButtonDefaults.IconSize),
                        painter = painterResource(
                            id = if (preferencesState().darkTheme == false) R.drawable.icon_light_mode_fill1_wght400 else
                                R.drawable.icon_light_mode_fill0_wght400
                        ),
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = stringResource(id = R.string.light))
                },
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = MaterialTheme.colorScheme.surface,
                    activeContentColor = MaterialTheme.colorScheme.primary,
                    inactiveContainerColor = Color.Transparent,
                    inactiveContentColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
            HorizontalSegmentedButton(
                onClick = { appearanceUiEvent(AppearanceUiEvent.SetTheme(null)) },
                selected = preferencesState().darkTheme == null,
                shape = MaterialTheme.shapes.medium,
                icon = {
                    Icon(
                        modifier = Modifier.size(SegmentedButtonDefaults.IconSize),
                        painter = painterResource(
                            id = if (preferencesState().darkTheme == null) R.drawable.icon_settings_night_sight_fill1_wght400 else
                                R.drawable.icon_settings_night_sight_fill0_wght400
                        ),
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = stringResource(id = R.string.system))
                },
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = MaterialTheme.colorScheme.surface,
                    activeContentColor = MaterialTheme.colorScheme.primary,
                    inactiveContainerColor = Color.Transparent,
                    inactiveContentColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
            HorizontalSegmentedButton(
                onClick = { appearanceUiEvent(AppearanceUiEvent.SetTheme(true)) },
                selected = preferencesState().darkTheme == true,
                shape = MaterialTheme.shapes.medium,
                icon = {
                    Icon(
                        modifier = Modifier.size(SegmentedButtonDefaults.IconSize),
                        painter = painterResource(
                            id = if (preferencesState().darkTheme == true) R.drawable.icon_dark_mode_fill1_wght400 else
                                R.drawable.icon_dark_mode_fill0_wght400
                        ),
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = stringResource(id = R.string.dark))
                },
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = MaterialTheme.colorScheme.surface,
                    activeContentColor = MaterialTheme.colorScheme.primary,
                    inactiveContainerColor = Color.Transparent,
                    inactiveContentColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    }
}
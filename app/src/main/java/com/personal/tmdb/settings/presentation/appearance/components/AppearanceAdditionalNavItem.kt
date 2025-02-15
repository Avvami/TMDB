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
import androidx.compose.ui.util.fastForEach
import com.personal.tmdb.R
import com.personal.tmdb.core.domain.util.AdditionalNavigationItem
import com.personal.tmdb.core.domain.util.AdditionalNavigationItem.Companion.toUiText
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.SegmentedButtonsRow
import com.personal.tmdb.core.presentation.components.VerticalSegmentedButton
import com.personal.tmdb.settings.presentation.appearance.AppearanceUiEvent

@Composable
fun AppearanceAdditionalNavItem(
    modifier: Modifier = Modifier,
    preferencesState: () -> PreferencesState,
    appearanceUiEvent: (AppearanceUiEvent) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.additional_nav_item),
            style = MaterialTheme.typography.titleMedium
        )
        SegmentedButtonsRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            AdditionalNavigationItem.entries.fastForEach { item ->
                VerticalSegmentedButton(
                    onClick = { appearanceUiEvent(AppearanceUiEvent.SetAdditionalNavItem(item)) },
                    selected = preferencesState().additionalNavigationItem == item,
                    shape = MaterialTheme.shapes.medium,
                    icon = {
                        Icon(
                            modifier = Modifier.size(SegmentedButtonDefaults.IconSize),
                            painter = painterResource(id = if (preferencesState().additionalNavigationItem == item) item.selectedIconRes else item.iconRes),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = item.toUiText().asString())
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
}
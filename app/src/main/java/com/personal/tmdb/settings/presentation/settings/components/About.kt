package com.personal.tmdb.settings.presentation.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.personal.tmdb.BuildConfig
import com.personal.tmdb.R
import com.personal.tmdb.core.presentation.components.CustomListItem
import com.personal.tmdb.core.domain.util.C
import com.personal.tmdb.settings.presentation.settings.SettingsUiEvent

@Composable
fun About(
    modifier: Modifier = Modifier,
    settingsUiEvent: (SettingsUiEvent) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.about),
            style = MaterialTheme.typography.titleMedium
        )
        CustomListItem(
            enabled = false,
            onClick = {},
            leadingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_radio_button_unchecked_fill0_wght400),
                    contentDescription = null
                )
            },
            headlineContent = {
                Text(text = stringResource(id = R.string.version))
            },
            trailingContent = {
                Text(text = stringResource(id = R.string.app_version, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE))
            }
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.not_certified),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.surfaceVariant
        )
        CustomListItem(
            onClick = { settingsUiEvent(SettingsUiEvent.OpenLink(C.GITHUB_PROJECT)) },
            leadingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_github_fill0),
                    contentDescription = null
                )
            },
            headlineContent = {
                Text(text = stringResource(id = R.string.github))
            },
            trailingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_open_in_new_fill0_wght400),
                    contentDescription = null
                )
            }
        )
        CustomListItem(
            onClick = { settingsUiEvent(SettingsUiEvent.OpenLink(C.TMDB_SUPPORT_FORUMS)) },
            leadingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_forum_fill0_wght400),
                    contentDescription = null
                )
            },
            headlineContent = {
                Text(text = stringResource(id = R.string.support_forums))
            },
            trailingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_open_in_new_fill0_wght400),
                    contentDescription = null
                )
            }
        )
        CustomListItem(
            onClick = { settingsUiEvent(SettingsUiEvent.OpenLink(C.TMDB_PRIVACY_POLICY)) },
            leadingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_policy_fill0_wght400),
                    contentDescription = null
                )
            },
            headlineContent = {
                Text(text = stringResource(id = R.string.privacy_policy))
            },
            trailingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_open_in_new_fill0_wght400),
                    contentDescription = null
                )
            }
        )
    }
}
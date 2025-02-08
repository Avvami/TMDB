package com.personal.tmdb.detail.presentation.episode.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.core.domain.util.formatDate
import com.personal.tmdb.core.domain.util.formatRuntime
import com.personal.tmdb.detail.domain.models.EpisodeDetailsInfo

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EpisodeTitle(
    modifier: Modifier = Modifier,
    episodeInfo: () -> EpisodeDetailsInfo
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        episodeInfo().name?.let { name ->
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium
            )
        }
        buildList<@Composable FlowRowScope.() -> Unit> {
            episodeInfo().airDate?.let { airDate ->
                add {
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = formatDate(airDate),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
            episodeInfo().runtime?.let { runtime ->
                add {
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = formatRuntime(runtime).asString(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        }.takeIf { it.isNotEmpty() }?.let { components ->
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                components.forEachIndexed { index, component ->
                    component()
                    if (index != components.lastIndex) {
                        Icon(
                            modifier = Modifier
                                .size(6.dp)
                                .align(Alignment.CenterVertically),
                            painter = painterResource(id = R.drawable.icon_fiber_manual_record_fill1_wght400),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                }
            }
        }
    }
}
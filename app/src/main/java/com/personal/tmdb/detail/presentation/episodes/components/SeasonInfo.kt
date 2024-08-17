package com.personal.tmdb.detail.presentation.episodes.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.util.formatEpisodesCount
import com.personal.tmdb.core.util.formatVoteAverage
import com.personal.tmdb.core.util.shimmerEffect
import com.personal.tmdb.detail.domain.models.SeasonInfo
import com.personal.tmdb.detail.presentation.episodes.EpisodesUiEvent

@OptIn(ExperimentalLayoutApi::class)
fun LazyListScope.seasonInfo(
    onNavigateTo: (route: String) -> Unit,
    seasonInfo: () -> SeasonInfo,
    isOverviewCollapsed: () -> Boolean,
    preferencesState: State<PreferencesState>,
    episodesUiEvent: (EpisodesUiEvent) -> Unit
) {
    item {
        with(seasonInfo()) {
            buildList<@Composable FlowRowScope.() -> Unit> {
                episodes?.let { episodes ->
                    if (episodes.isNotEmpty()) {
                        add {
                            Text(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                text = formatEpisodesCount(episodes.size),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                voteAverage?.let { voteAverage ->
                    if (voteAverage.toDouble() != 0.0) {
                        add {
                            Row(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Text(
                                    text = formatVoteAverage(voteAverage),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    painter = painterResource(id = R.drawable.icon_bar_chart_fill0_wght400),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }.takeIf { it.isNotEmpty() }?.let { components ->
                FlowRow(
                    modifier = Modifier.padding(start = 16.dp, top = 2.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
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
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
    seasonInfo().overview?.let { overview ->
        item {
            SelectionContainer {
                Column(
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.overview),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.extraSmall)
                            .animateContentSize()
                            .clickable {
                                episodesUiEvent(EpisodesUiEvent.ChangeCollapsedOverview)
                            },
                        text = overview,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = if (isOverviewCollapsed()) 3 else Int.MAX_VALUE,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
    seasonInfo().episodes?.let { episodes ->
        if (episodes.isEmpty()) {
            item {
                Text(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.no_episodes),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            items(
                count = episodes.size,
                key = { episodes[it].id }
            ) { index ->
                val episodeInfo = episodes[index]
                Episode(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clip(MaterialTheme.shapes.extraSmall)
                        .clickable { /*TODO: Navigate to episode detail*/ },
                    episodeInfo = { episodeInfo },
                    preferencesState = preferencesState
                )
                if (index < episodes.lastIndex) {
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                }
            }
        }
    }
}

fun LazyListScope.seasonInfoShimmer() {
    item {
        Text(
            modifier = Modifier
                .padding(start = 16.dp, top = 2.dp, end = 15.dp)
                .clip(MaterialTheme.shapes.small)
                .shimmerEffect(),
            text = "Episodes and rating",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Transparent
        )
    }
    items(8) { index ->
        EpisodeShimmer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        if (index < 7) {
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}
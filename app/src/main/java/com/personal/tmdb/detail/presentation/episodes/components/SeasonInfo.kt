package com.personal.tmdb.detail.presentation.episodes.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.core.navigation.Route
import com.personal.tmdb.detail.presentation.episodes.EpisodesUiEvent
import com.personal.tmdb.detail.presentation.episodes.SeasonState

fun LazyListScope.seasonInfo(
    seasonState: () -> SeasonState,
    episodesUiEvent: (EpisodesUiEvent) -> Unit
) {
    if (seasonState().loading) {
        items(10) {
            EpisodeShimmer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    } else {
        seasonState().seasonInfo?.let { seasonInfo ->
            seasonInfo.overview?.let { overview ->
                item {
                    SelectionContainer {
                        Column(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.overview),
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateContentSize()
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        episodesUiEvent(EpisodesUiEvent.ChangeOverviewState)
                                    },
                                text = overview,
                                style = MaterialTheme.typography.bodyLarge,
                                maxLines = if (seasonState().overviewCollapsed) 3 else Int.MAX_VALUE,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
            seasonInfo.episodes?.let { episodes ->
                if (episodes.isEmpty()) {
                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            text = stringResource(id = R.string.no_episodes),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    items(
                        items = episodes,
                        key = { it.id }
                    ) { episode ->
                        Episode(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    episodesUiEvent(
                                        EpisodesUiEvent.OnNavigateTo(
                                            Route.Episode(
                                                mediaId = seasonState().mediaId,
                                                seasonNumber = seasonInfo.seasonNumber,
                                                episodeNumber = episode.episodeNumber
                                            )
                                        )
                                    )
                                }
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            episodeInfo = episode
                        )
                    }
                }
            }
        }
    }
}
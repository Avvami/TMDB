package com.personal.tmdb.detail.presentation.detail.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.core.navigation.Route
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.formatDate
import com.personal.tmdb.core.util.formatRuntime
import com.personal.tmdb.detail.data.models.Season
import com.personal.tmdb.detail.domain.models.EpisodeToAirInfo
import com.personal.tmdb.detail.domain.models.MediaDetailInfo
import com.personal.tmdb.detail.presentation.detail.DetailUiEvent

@Composable
fun DetailEpisodes(
    modifier: Modifier = Modifier,
    info: MediaDetailInfo,
    seasons: List<Season>,
    detailUiEvent: (DetailUiEvent) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    detailUiEvent(
                        DetailUiEvent.OnNavigateTo(
                            Route.Episodes(
                                mediaId = info.id,
                                seasonNumber = info.lastEpisodeToAir?.seasonNumber
                                    ?: seasons.find { it.seasonNumber == 1 }?.seasonNumber
                                    ?: seasons[seasons.lastIndex].seasonNumber
                            )
                        )
                    )
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.episodes),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.all_episodes),
                    style = MaterialTheme.typography.bodyLarge
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                    contentDescription = null
                )
            }
        }
        info.lastEpisodeToAir?.let { lastEpisodeToAir ->
            EpisodeToAir(
                onNavigateTo = { detailUiEvent(DetailUiEvent.OnNavigateTo(it)) },
                episodeToAir = lastEpisodeToAir,
                subTextRes = R.string.last_episode
            )
        }
        info.nextEpisodeToAir?.let { nextEpisodeToAir ->
            EpisodeToAir(
                onNavigateTo = { detailUiEvent(DetailUiEvent.OnNavigateTo(it)) },
                episodeToAir = nextEpisodeToAir,
                subTextRes = R.string.next_episode
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EpisodeToAir(
    modifier: Modifier = Modifier,
    onNavigateTo: (route: Route) -> Unit,
    episodeToAir: EpisodeToAirInfo,
    @StringRes subTextRes: Int? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable {
                onNavigateTo(
                    Route.Episode(
                        mediaId = episodeToAir.showId,
                        seasonNumber = episodeToAir.seasonNumber,
                        episodeNumber = episodeToAir.episodeNumber
                    )
                )
            }
            .padding(2.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .height(80.dp)
                .aspectRatio(16 / 9f)
                .clip(RoundedCornerShape(10.dp)),
            model = C.TMDB_IMAGES_BASE_URL + C.STILL_W300 + episodeToAir.stillPath,
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.placeholder),
            contentDescription = "Still",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.End
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                with(episodeToAir) {
                    episodeToAir.name?.let { name ->
                        Text(
                            text = "${
                                stringResource(
                                    id = R.string.season_episode,
                                    episodeToAir.seasonNumber,
                                    episodeToAir.episodeNumber
                                )
                            } $name",
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    buildList<@Composable FlowRowScope.() -> Unit> {
                        airDate?.let { airDate ->
                            add {
                                Text(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    text = formatDate(airDate),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                        runtime?.let { runtime ->
                            add {
                                val context = LocalContext.current
                                Text(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    text = formatRuntime(runtime, context),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }.takeIf { it.isNotEmpty() }?.let { components ->
                    FlowRow(
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
                                    tint = MaterialTheme.colorScheme.surfaceVariant
                                )
                            }
                        }
                    }
                }
            }
            subTextRes?.let {
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = stringResource(id = it),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
    }
}
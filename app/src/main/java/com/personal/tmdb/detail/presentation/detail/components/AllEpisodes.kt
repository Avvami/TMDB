package com.personal.tmdb.detail.presentation.detail.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.State
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
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.core.navigation.RootNavGraph
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.formatDate
import com.personal.tmdb.core.util.formatRuntime
import com.personal.tmdb.core.util.formatVoteAverage
import com.personal.tmdb.detail.data.models.Season
import com.personal.tmdb.detail.domain.models.EpisodeToAirInfo
import com.personal.tmdb.detail.domain.models.MediaDetailInfo

@Composable
fun AllEpisodes(
    onNavigateTo: (route: String) -> Unit,
    info: MediaDetailInfo,
    seasons: List<Season>,
    preferencesState: State<PreferencesState>
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.extraSmall)
                .clickable {
                    onNavigateTo(RootNavGraph.EPISODES + "/${info.id}/${
                        info.nextEpisodeToAir?.seasonNumber
                            ?: info.lastEpisodeToAir?.seasonNumber ?: seasons.find { it.seasonNumber == 1 } ?: seasons[0]
                    }")
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.all_episodes),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            )
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = null
            )
        }
        info.lastEpisodeToAir?.let { lastEpisodeToAir ->
            EpisodeToAir(
                onNavigateTo = onNavigateTo,
                preferencesState = preferencesState,
                episodeToAir = lastEpisodeToAir,
                textRes = R.string.last_episode
            )
        }
        info.nextEpisodeToAir?.let { nextEpisodeToAir ->
            EpisodeToAir(
                onNavigateTo = onNavigateTo,
                preferencesState = preferencesState,
                episodeToAir = nextEpisodeToAir,
                textRes = R.string.next_episode
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EpisodeToAir(
    onNavigateTo: (route: String) -> Unit,
    preferencesState: State<PreferencesState>,
    episodeToAir: EpisodeToAirInfo,
    @StringRes textRes: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(preferencesState.value.corners.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable { onNavigateTo(RootNavGraph.EPISODE + "/${episodeToAir.showId}/${episodeToAir.seasonNumber}/${episodeToAir.episodeNumber}") }
            .padding(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(80.dp)
                    .aspectRatio(1.625f)
                    .clip(RoundedCornerShape(preferencesState.value.corners.dp)),
                model = C.TMDB_IMAGES_BASE_URL + C.STILL_W300 + episodeToAir.stillPath,
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.placeholder),
                contentDescription = "Still",
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                with(episodeToAir) {
                    episodeToAir.name?.let { name ->
                        Text(
                            text = "${stringResource(
                                id = R.string.season_episode,
                                episodeToAir.seasonNumber,
                                episodeToAir.episodeNumber
                            )} $name",
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    buildList<@Composable FlowRowScope.() -> Unit> {
                        voteAverage?.let { voteAverage ->
                            if (voteAverage.toDouble() != 0.0) {
                                add {
                                    Row(
                                        modifier = Modifier
                                            .clip(MaterialTheme.shapes.extraSmall)
                                            .background(MaterialTheme.colorScheme.inverseSurface)
                                            .padding(
                                                horizontal = 4.dp,
                                                vertical = 2.dp
                                            )
                                            .align(Alignment.CenterVertically),
                                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = formatVoteAverage(voteAverage),
                                            style = MaterialTheme.typography.labelMedium,
                                            color = MaterialTheme.colorScheme.inverseOnSurface
                                        )
                                        Icon(
                                            modifier = Modifier.size(14.dp),
                                            painter = painterResource(id = R.drawable.icon_bar_chart_fill0_wght400),
                                            contentDescription = "Rating",
                                            tint = MaterialTheme.colorScheme.inverseOnSurface
                                        )
                                    }
                                }
                            }
                        }
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
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
        Text(
            modifier = Modifier.align(Alignment.End),
            text = stringResource(id = textRes),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
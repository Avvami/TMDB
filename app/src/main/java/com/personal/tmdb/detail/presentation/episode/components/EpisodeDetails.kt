package com.personal.tmdb.detail.presentation.episode.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.core.util.formatDate
import com.personal.tmdb.core.util.formatRuntime
import com.personal.tmdb.core.util.formatVoteAverage
import com.personal.tmdb.core.util.formatVoteAverageToColor
import com.personal.tmdb.detail.domain.models.EpisodeDetailsInfo
import com.personal.tmdb.detail.presentation.episode.EpisodeDetailsUiEvent
import com.personal.tmdb.ui.theme.justWatch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EpisodeDetails(
    info: () -> EpisodeDetailsInfo,
    isOverviewCollapsed: () -> Boolean,
    episodeDetailsUiEvent: (EpisodeDetailsUiEvent) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            with(info()) {
                name?.let { name ->
                    Text(
                        text = stringResource(
                            id = R.string.episode_name,
                            episodeNumber,
                            name
                        ),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
                buildList<@Composable FlowRowScope.() -> Unit> {
                    airDate?.let { airDate ->
                        add {
                            Text(
                                text = formatDate(airDate),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }
                    runtime?.let { runtime ->
                        add {
                            val context = LocalContext.current
                            Text(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                text = formatRuntime(runtime, context),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }.takeIf { it.isNotEmpty() }?.let { components ->
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
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
        info().voteAverage?.let { voteAverage ->
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    progress = { voteAverage.div(10f) },
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    color = formatVoteAverageToColor(voteAverage)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .sizeIn(minWidth = 32.dp, minHeight = 32.dp)
                                .clip(MaterialTheme.shapes.small)
                                .background(justWatch),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(id = R.drawable.tmdb_logo_square),
                                contentDescription = stringResource(id = R.string.tmdb)
                            )
                        }
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Text(
                                    text = formatVoteAverage(voteAverage),
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Icon(
                                    modifier = Modifier.size(14.dp),
                                    painter = painterResource(id = R.drawable.icon_bar_chart_fill0_wght400),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            info().voteCount?.takeIf { it != 0 }?.let { voteCount ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Text(
                                        text = voteCount.toString(),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Icon(
                                        modifier = Modifier.size(12.dp),
                                        painter = painterResource(id = R.drawable.icon_group_fill0_wght400),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .clickable { /*TODO: Show rate bottom sheet*/ },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .sizeIn(minWidth = 32.dp, minHeight = 32.dp)
                                .clip(MaterialTheme.shapes.small)
                                .background(MaterialTheme.colorScheme.surfaceContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            if (/*TODO: Not rated by user*/true) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Icons.Rounded.Add,
                                    contentDescription = "Add rating",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            } else {
                                /*TODO: User rating*/
                            }
                        }
                        if (/*TODO: Not rated by user*/true) {
                            Text(
                                text = stringResource(id = R.string.rate_now),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        } else {
                            /*TODO: User rating*/
                        }
                    }
                }
            }
        }
        info().overview?.let { overview ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.extraSmall)
                    .animateContentSize()
                    .clickable {
                        episodeDetailsUiEvent(EpisodeDetailsUiEvent.ChangeCollapsedOverview)
                    },
                text = overview,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = if (isOverviewCollapsed()) 4 else Int.MAX_VALUE,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
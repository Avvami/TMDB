package com.personal.tmdb.detail.presentation.episodes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.formatDate
import com.personal.tmdb.core.util.formatRuntime
import com.personal.tmdb.core.util.shimmerEffect
import com.personal.tmdb.detail.domain.models.EpisodeInfo

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Episode(
    modifier: Modifier = Modifier,
    episodeInfo: EpisodeInfo
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .height(80.dp)
                .aspectRatio(16 / 9f)
                .clip(MaterialTheme.shapes.medium),
            model = C.TMDB_IMAGES_BASE_URL + C.STILL_W300 + episodeInfo.stillPath,
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.placeholder),
            contentDescription = "Still",
            contentScale = ContentScale.Crop
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            with(episodeInfo) {
                name?.let { name ->
                    Text(
                        text = stringResource(
                            id = R.string.episode_name,
                            episodeNumber,
                            name
                        ),
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                buildList<@Composable FlowRowScope.() -> Unit> {
                    airDate?.let { airDate ->
                        add {
                            Text(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                text = formatDate(airDate),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.surfaceVariant
                            )
                        }
                    }
                    runtime?.let { runtime ->
                        add {
                            Text(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                text = formatRuntime(runtime).asString(),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.surfaceVariant
                            )
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
        }
    }
}

@Composable
fun EpisodeShimmer(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .height(80.dp)
                .aspectRatio(16 / 9f)
                .clip(MaterialTheme.shapes.medium)
                .shimmerEffect()
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraSmall)
                    .shimmerEffect(),
                text = "0. Episode title",
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                color = Color.Transparent
            )
            Text(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraSmall)
                    .shimmerEffect(),
                text = "00 Episodes",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Transparent
            )
        }
    }
}
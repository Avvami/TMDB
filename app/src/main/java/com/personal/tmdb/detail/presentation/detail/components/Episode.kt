package com.personal.tmdb.detail.presentation.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.formatDate
import com.personal.tmdb.core.util.formatRuntime
import com.personal.tmdb.core.util.formatVoteAverage
import com.personal.tmdb.core.util.shimmerEffect
import com.personal.tmdb.detail.domain.models.EpisodeInfo

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Episode(
    modifier: Modifier = Modifier,
    episodeInfo: () -> EpisodeInfo,
    preferencesState: State<PreferencesState>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(80.dp)
                    .aspectRatio(1.625f)
                    .clip(RoundedCornerShape(preferencesState.value.corners.dp)),
                model = C.TMDB_IMAGES_BASE_URL + C.STILL_W300 + episodeInfo().stillPath,
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.placeholder),
                contentDescription = "Poster",
                contentScale = ContentScale.Crop
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = episodeInfo().episodeNumber.toString(),
                        style = MaterialTheme.typography.titleMedium
                    )
                    episodeInfo().name?.let { name ->
                        Text(
                            text = name,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                FlowRow(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    episodeInfo().voteAverage?.let { voteAverage ->
                        Row(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.extraSmall)
                                .background(MaterialTheme.colorScheme.inverseSurface)
                                .padding(horizontal = 4.dp, vertical = 2.dp)
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
                    episodeInfo().airDate?.let { airDate ->
                        Icon(
                            modifier = Modifier
                                .size(6.dp)
                                .align(Alignment.CenterVertically),
                            painter = painterResource(id = R.drawable.icon_fiber_manual_record_fill1_wght400),
                            contentDescription = null
                        )
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = formatDate(airDate),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    episodeInfo().runtime?.let { runtime ->
                        val context = LocalContext.current
                        Icon(
                            modifier = Modifier
                                .size(6.dp)
                                .align(Alignment.CenterVertically),
                            painter = painterResource(id = R.drawable.icon_fiber_manual_record_fill1_wght400),
                            contentDescription = null
                        )
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = formatRuntime(runtime, context),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
        episodeInfo().overview?.let { overview ->
            Text(
                text = overview,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EpisodeShimmer(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(80.dp)
                    .aspectRatio(1.625f)
                    .clip(RoundedCornerShape(18.dp))
                    .shimmerEffect()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(id = R.string.tmdb),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .shimmerEffect(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "0",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Transparent
                    )
                    Text(
                        text = "Episode",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Transparent
                    )
                }
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.small)
                        .shimmerEffect(),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.extraSmall)
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                            .align(Alignment.CenterVertically),
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "0",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Transparent
                        )
                        Icon(
                            modifier = Modifier.size(14.dp),
                            painter = painterResource(id = R.drawable.icon_bar_chart_fill0_wght400),
                            contentDescription = "Rating",
                            tint = Color.Transparent
                        )
                    }
                }
            }
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.small)
                .shimmerEffect(),
            text = "",
            style = MaterialTheme.typography.bodyMedium,
            minLines = 3
        )
    }
}
package com.personal.tmdb.detail.presentation.detail.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.core.navigation.RootNavGraph
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.formatDate
import com.personal.tmdb.core.util.formatRuntime
import com.personal.tmdb.core.util.formatTvShowRuntime
import com.personal.tmdb.core.util.formatVoteAverage
import com.personal.tmdb.core.util.formatVoteAverageToColor
import com.personal.tmdb.core.util.shimmerEffect
import com.personal.tmdb.detail.domain.models.MediaDetailInfo
import com.personal.tmdb.detail.presentation.collection.CollectionState
import com.personal.tmdb.detail.presentation.detail.DetailUiEvent
import com.personal.tmdb.ui.theme.backgroundLight
import com.personal.tmdb.ui.theme.tmdbDarkBlue

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Details(
    onNavigateTo: (route: String) -> Unit,
    info: () -> MediaDetailInfo,
    collectionState: () -> CollectionState,
    isOverviewCollapsed: () -> Boolean,
    detailUiEvent: (DetailUiEvent) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                info().name?.let { title ->
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    info().releaseDate?.let { releaseDate ->
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = formatDate(releaseDate),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    info().contentRatings?.contentRatingsResults?.find { it.iso31661 == "US" }?.let { result ->
                        if (result.rating.isNotEmpty()) {
                            Icon(
                                modifier = Modifier
                                    .size(6.dp)
                                    .align(Alignment.CenterVertically),
                                painter = painterResource(id = R.drawable.icon_fiber_manual_record_fill1_wght400),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.extraSmall)
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                                    .padding(horizontal = 8.dp)
                                    .align(Alignment.CenterVertically),
                                text = result.rating,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    info().releaseDates?.releaseDatesResults?.find { it.iso31661 == "US" }?.let { result ->
                        result.releaseDates?.find { it.certification.isNotEmpty() }?.let { release ->
                            if (release.certification.isNotEmpty()) {
                                Icon(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .align(Alignment.CenterVertically),
                                    painter = painterResource(id = R.drawable.icon_fiber_manual_record_fill1_wght400),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    modifier = Modifier
                                        .clip(MaterialTheme.shapes.extraSmall)
                                        .background(MaterialTheme.colorScheme.surfaceVariant)
                                        .padding(horizontal = 8.dp)
                                        .align(Alignment.CenterVertically),
                                    text = release.certification,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                    info().runtime?.let { runtime ->
                        Icon(
                            modifier = Modifier
                                .size(6.dp)
                                .align(Alignment.CenterVertically),
                            painter = painterResource(id = R.drawable.icon_fiber_manual_record_fill1_wght400),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = formatRuntime(runtime, context),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    if (info().numberOfSeasons != null && info().numberOfEpisodes != null) {
                        Icon(
                            modifier = Modifier
                                .size(6.dp)
                                .align(Alignment.CenterVertically),
                            painter = painterResource(id = R.drawable.icon_fiber_manual_record_fill1_wght400),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = formatTvShowRuntime(info().numberOfSeasons!!, info().numberOfEpisodes!!),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            info().voteAverage?.let { voteAverage ->
                Column {
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
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = formatVoteAverage(voteAverage),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Icon(
                                modifier = Modifier.size(14.dp),
                                painter = painterResource(id = R.drawable.icon_bar_chart_fill0_wght400),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        if (false) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Text(
                                    text = "Your: 10",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Icon(
                                    modifier = Modifier.size(14.dp),
                                    painter = painterResource(id = R.drawable.icon_bar_chart_fill0_wght400),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
            info().tagline?.let { tagline ->
                Text(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(10.dp),
                    text = "\"$tagline\"",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            info().overview?.let { overview ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.extraSmall)
                        .animateContentSize()
                        .clickable {
                            detailUiEvent(DetailUiEvent.ChangeCollapsedOverview)
                        },
                    text = overview,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = if (isOverviewCollapsed()) 4 else Int.MAX_VALUE,
                    overflow = TextOverflow.Ellipsis
                )
            }
            info().credits?.cast?.let { cast ->
                if (cast.isNotEmpty()) {
                    Column {
                        info().createdBy?.firstOrNull()?.let { createdBy ->
                            createdBy.name?.let { name ->
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.creator),
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = name,
                                        style = MaterialTheme.typography.bodyMedium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                        info().credits?.crew?.find { it.department == "Directing" }?.let { director ->
                            director.name?.let { name ->
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.director),
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = name,
                                        style = MaterialTheme.typography.bodyMedium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(2.dp))
                                .clickable { /*TODO: Go to cast screen*/ },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Row(
                                modifier = Modifier.weight(1f, false),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.starring),
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = cast.take(5).joinToString(", ") { it.name.toString() },
                                    style = MaterialTheme.typography.bodyMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Text(
                                text = stringResource(id = R.string.more),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            info().belongsToCollection?.let { belongToCollection ->
                Box(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .clip(MaterialTheme.shapes.large)
                        .clickable { onNavigateTo(RootNavGraph.COLLECTION + "/${belongToCollection.id}") }
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = C.TMDB_IMAGES_BASE_URL + C.BACKDROP_W1280 + belongToCollection.backdropPath,
                        contentDescription = "Backdrop",
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.placeholder),
                        error = painterResource(id = R.drawable.placeholder)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(tmdbDarkBlue.copy(alpha = .7f))
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.part_of_collection, belongToCollection.name ?: ""),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium,
                            color = backgroundLight
                        )
                        if (collectionState().isLoading) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(MaterialTheme.shapes.small)
                                    .shimmerEffect(backgroundLight, backgroundLight.copy(alpha = .3f)),
                                text = "",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Transparent,
                                minLines = 2,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        } else {
                            collectionState().collectionInfo?.parts?.let { partInfos ->
                                Text(
                                    text = stringResource(id = R.string.includes, partInfos.joinToString(", ") { it.name.toString() }),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = backgroundLight,
                                    minLines = 2,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
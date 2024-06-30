package com.personal.tmdb.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.core.domain.models.MediaInfo
import com.personal.tmdb.core.navigation.RootNavGraph
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.core.util.formatDate
import com.personal.tmdb.core.util.formatVoteAverage
import com.personal.tmdb.ui.theme.backgroundLight
import com.personal.tmdb.ui.theme.onBackgroundLight

@Composable
fun MediaCard(
    modifier: Modifier = Modifier,
    onNavigateTo: (route: String) -> Unit,
    mediaInfo: MediaInfo,
    mediaType: MediaType? = null,
    showVoteAverage: Boolean
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .clickable {
                mediaType?.let { mediaType ->
                    when (mediaType) {
                        MediaType.TV, MediaType.MOVIE -> {
                            onNavigateTo(RootNavGraph.DETAIL + "/${mediaType.name.lowercase()}/${mediaInfo.id}")
                        }
                        MediaType.PERSON -> {
                            /*TODO: Navigate to person screen*/
                        }
                        else -> {
                            /*TODO: Navigate to lost your way screen*/
                        }
                    }
                }
                mediaInfo.mediaType?.let { mediaType ->
                    when (mediaType) {
                        MediaType.TV, MediaType.MOVIE -> {
                            onNavigateTo(RootNavGraph.DETAIL + "/${mediaType.name.lowercase()}/${mediaInfo.id}")
                        }
                        MediaType.PERSON -> {
                            /*TODO: Navigate to person screen*/
                        }
                        else -> {
                            /*TODO: Navigate to lost your way screen*/
                        }
                    }
                }
            }
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .height(150.dp)
                .aspectRatio(0.675f)
                .clip(RoundedCornerShape(18.dp))
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = C.TMDB_IMAGES_BASE_URL + C.POSTER_W300 + mediaInfo.posterPath,
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.placeholder),
                contentDescription = "Poster",
                contentScale = ContentScale.Crop
            )
            if (showVoteAverage && mediaInfo.voteAverage != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(onBackgroundLight.copy(.3f))
                        .align(Alignment.BottomCenter),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = formatVoteAverage(mediaInfo.voteAverage),
                        style = MaterialTheme.typography.labelLarge,
                        color = backgroundLight
                    )
                    Icon(
                        modifier = Modifier.size(14.dp),
                        painter = painterResource(id = R.drawable.icon_bar_chart_fill0_wght400),
                        contentDescription = null,
                        tint = backgroundLight
                    )
                }
            }
        }
        Column {
            mediaInfo.name?.let { name ->
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            mediaInfo.releaseDate?.let { releaseDate ->
                Text(
                    text = formatDate(releaseDate),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            mediaInfo.overview?.let { overview ->
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = overview,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun MediaPoster(
    modifier: Modifier = Modifier,
    onNavigateTo: (route: String) -> Unit,
    mediaInfo: MediaInfo,
    mediaType: MediaType? = null,
    showTitle: Boolean,
    showVoteAverage: Boolean
) {
    Column(
        modifier = Modifier.width(IntrinsicSize.Min),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .clickable {
                    mediaType?.let { mediaType ->
                        when (mediaType) {
                            MediaType.TV, MediaType.MOVIE -> {
                                onNavigateTo(RootNavGraph.DETAIL + "/${mediaType.name.lowercase()}/${mediaInfo.id}")
                            }
                            MediaType.PERSON -> {
                                /*TODO: Navigate to person screen*/
                            }
                            else -> {
                                /*TODO: Navigate to lost your way screen*/
                            }
                        }
                    }
                    mediaInfo.mediaType?.let { mediaType ->
                        when (mediaType) {
                            MediaType.TV, MediaType.MOVIE -> {
                                onNavigateTo(RootNavGraph.DETAIL + "/${mediaType.name.lowercase()}/${mediaInfo.id}")
                            }
                            MediaType.PERSON -> {
                                /*TODO: Navigate to person screen*/
                            }
                            else -> {
                                /*TODO: Navigate to lost your way screen*/
                            }
                        }
                    }
                }
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = C.TMDB_IMAGES_BASE_URL + C.POSTER_W300 + mediaInfo.posterPath,
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.placeholder),
                contentDescription = "Poster",
                contentScale = ContentScale.Crop
            )
            if (showVoteAverage && mediaInfo.voteAverage != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(onBackgroundLight.copy(.3f))
                        .align(Alignment.BottomCenter),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = formatVoteAverage(mediaInfo.voteAverage),
                        style = MaterialTheme.typography.labelLarge,
                        color = backgroundLight
                    )
                    Icon(
                        modifier = Modifier.size(14.dp),
                        painter = painterResource(id = R.drawable.icon_bar_chart_fill0_wght400),
                        contentDescription = null,
                        tint = backgroundLight
                    )
                }
            }
        }
        if (showTitle || mediaType?.equals(MediaType.PERSON) == true) {
            mediaInfo.name?.let { name ->
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = name,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    minLines = 2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun MediaPosterShimmer(
    modifier: Modifier = Modifier,
    showTitle: Boolean
) {
    Column(
        modifier = Modifier.width(IntrinsicSize.Min),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = stringResource(id = R.string.tmdb),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center
            )
        }
        if (showTitle) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "",
                minLines = 2,
                maxLines = 2,
            )
        }
    }
}
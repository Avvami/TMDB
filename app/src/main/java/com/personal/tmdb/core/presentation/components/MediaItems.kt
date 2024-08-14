package com.personal.tmdb.core.presentation.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.personal.tmdb.core.util.shimmerEffect
import com.personal.tmdb.ui.theme.backgroundLight
import com.personal.tmdb.ui.theme.onBackgroundLight
import java.time.LocalDate

@Composable
fun MediaCard(
    modifier: Modifier = Modifier,
    onNavigateTo: (route: String) -> Unit,
    mediaInfo: MediaInfo,
    mediaType: MediaType? = null,
    showVoteAverage: Boolean,
    corners: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(corners.dp))
            .clickable {
                mediaType?.let { mediaType ->
                    when (mediaType) {
                        MediaType.TV, MediaType.MOVIE -> {
                            onNavigateTo(RootNavGraph.DETAIL + "/${mediaType.name.lowercase()}/${mediaInfo.id}")
                        }
                        MediaType.PERSON -> {
                            onNavigateTo(RootNavGraph.PERSON + "/${mediaInfo.name ?: ""}/${mediaInfo.id}")
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
                            onNavigateTo(RootNavGraph.PERSON + "/${mediaInfo.name ?: ""}/${mediaInfo.id}")
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
                .clip(RoundedCornerShape(corners.dp))
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
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape((corners / 3).dp))
                        .background(onBackgroundLight.copy(.5f))
                        .padding(horizontal = 4.dp)
                        .align(Alignment.TopStart),
                    text = formatVoteAverage(8.5.toFloat()),
                    style = MaterialTheme.typography.labelMedium,
                    color = backgroundLight
                )
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
fun MediaCardShimmer(
    modifier: Modifier = Modifier,
    corners: Int
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(corners.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .height(150.dp)
                .aspectRatio(0.675f)
                .clip(RoundedCornerShape(corners.dp))
                .shimmerEffect()
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
        Column {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect(),
                text = "",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect(),
                text = "release date",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Transparent
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect(),
                text = "",
                style = MaterialTheme.typography.bodyMedium,
                minLines = 4
            )
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
    showVoteAverage: Boolean,
    corners: Int
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
                                onNavigateTo(RootNavGraph.PERSON + "/${mediaInfo.name ?: ""}/${mediaInfo.id}")
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
                                onNavigateTo(RootNavGraph.PERSON + "/${mediaInfo.name ?: ""}/${mediaInfo.id}")
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
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape((corners / 3).dp))
                        .background(onBackgroundLight.copy(.5f))
                        .padding(horizontal = 4.dp)
                        .align(Alignment.TopStart),
                    text = formatVoteAverage(8.5.toFloat()),
                    style = MaterialTheme.typography.labelMedium,
                    color = backgroundLight
                )
            }
        }
        if (showTitle || mediaType?.equals(MediaType.PERSON) == true || mediaInfo.mediaType?.equals(MediaType.PERSON) == true) {
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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MediaCardPreview(
    modifier: Modifier = Modifier,
    showVoteAverage: Boolean,
    corners: Int,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(corners.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        with(sharedTransitionScope) {
            Box(
                modifier = Modifier
                    .sharedElement(
                        rememberSharedContentState(key = "Poster"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                    .height(150.dp)
                    .aspectRatio(0.675f)
                    .clip(RoundedCornerShape(corners.dp))
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = C.TMDB_IMAGES_BASE_URL + C.POSTER_W300 + "/39wmItIWsg5sZMyRUHLkWBcuVCM.jpg",
                    placeholder = painterResource(id = R.drawable.placeholder),
                    error = painterResource(id = R.drawable.placeholder),
                    contentDescription = "Poster",
                    contentScale = ContentScale.Crop
                )
                androidx.compose.animation.AnimatedVisibility(
                    visible = showVoteAverage,
                    enter = scaleIn(initialScale = .7f) + fadeIn(),
                    exit = scaleOut(targetScale = .7f) + fadeOut()
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape((corners / 3).dp))
                            .background(onBackgroundLight.copy(.5f))
                            .padding(horizontal = 4.dp)
                            .align(Alignment.TopStart),
                        text = formatVoteAverage(8.5.toFloat()),
                        style = MaterialTheme.typography.labelMedium,
                        color = backgroundLight
                    )
                }
            }
        }
        Column {
            Text(
                text = "Spirited Away",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = formatDate(LocalDate.of(2002, 9, 20)),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "A young girl, Chihiro, becomes trapped in a strange new world of spirits. When her parents undergo a mysterious transformation, she must call upon the courage she never knew she had to free her family.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MediaPosterPreview(
    modifier: Modifier = Modifier,
    showTitle: Boolean,
    showVoteAverage: Boolean,
    corners: Int,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Column(
        modifier = Modifier.width(IntrinsicSize.Min),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        with(sharedTransitionScope) {
            Box(
                modifier = modifier
                    .sharedElement(
                        rememberSharedContentState(key = "Poster"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                    .height(150.dp)
                    .aspectRatio(0.675f)
                    .clip(RoundedCornerShape(corners.dp))
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = C.TMDB_IMAGES_BASE_URL + C.POSTER_W300 + "/39wmItIWsg5sZMyRUHLkWBcuVCM.jpg",
                    placeholder = painterResource(id = R.drawable.placeholder),
                    error = painterResource(id = R.drawable.placeholder),
                    contentDescription = "Poster",
                    contentScale = ContentScale.Crop
                )
                androidx.compose.animation.AnimatedVisibility(
                    visible = showVoteAverage,
                    enter = scaleIn(initialScale = .7f) + fadeIn(),
                    exit = scaleOut(targetScale = .7f) + fadeOut()
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape((corners / 3).dp))
                            .background(onBackgroundLight.copy(.5f))
                            .padding(horizontal = 4.dp)
                            .align(Alignment.TopStart),
                        text = formatVoteAverage(8.5.toFloat()),
                        style = MaterialTheme.typography.labelMedium,
                        color = backgroundLight
                    )
                }
            }
        }
        androidx.compose.animation.AnimatedVisibility(
            visible = showTitle,
            enter = slideInVertically() + scaleIn(initialScale = .7f) + fadeIn(),
            exit = slideOutVertically() + scaleOut(targetScale = .7f) + fadeOut()
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Spirited Away",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
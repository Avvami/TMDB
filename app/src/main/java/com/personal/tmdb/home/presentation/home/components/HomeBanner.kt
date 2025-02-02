package com.personal.tmdb.home.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.personal.tmdb.core.navigation.Route
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.core.util.shimmerEffect
import com.personal.tmdb.home.presentation.home.HomeState
import com.personal.tmdb.home.presentation.home.HomeUiEvent
import com.personal.tmdb.ui.theme.surfaceLight

@Composable
fun HomeBanner(
    modifier: Modifier = Modifier,
    homeState: () -> HomeState,
    homeUiEvent: (HomeUiEvent) -> Unit
) {
    Box(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                homeState().randomMedia?.let { randomMedia ->
                    when (randomMedia.mediaType) {
                        MediaType.TV, MediaType.MOVIE -> {
                            homeUiEvent(
                                HomeUiEvent.OnNavigateTo(
                                    Route.Detail(
                                        mediaType = randomMedia.mediaType.name.lowercase(),
                                        mediaId = randomMedia.id
                                    )
                                )
                            )
                        }
                        MediaType.PERSON -> {
                            homeUiEvent(
                                HomeUiEvent.OnNavigateTo(
                                    Route.Person(
                                        personName = randomMedia.mediaType.name,
                                        personId = randomMedia.id
                                    )
                                )
                            )
                        }
                        else -> {
                            /*TODO: Navigate to lost your way screen*/
                        }
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(C.TMDB_IMAGES_BASE_URL + C.BACKDROP_W1280 + homeState().randomMedia?.backdropPath)
                .crossfade(true)
                .build(),
            contentDescription = "Backdrop",
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.scrim.copy(.2f), BlendMode.Darken)
        )
        if (homeState().randomMedia == null || homeState().loading) {
            BannerShimmer(
                modifier = Modifier
                    .padding(32.dp)
                    .align(Alignment.BottomCenter),
            )
        } else {
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                homeState().randomMedia?.name?.let { name ->
                    Text(
                        text = name,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                            lineHeight = MaterialTheme.typography.headlineLarge.lineHeight,
                            letterSpacing = MaterialTheme.typography.headlineLarge.letterSpacing,
                            fontWeight = FontWeight.SemiBold,
                            color = surfaceLight,
                            shadow = Shadow(color = MaterialTheme.colorScheme.scrim.copy(alpha = .5f), offset = Offset(1f, 2f), blurRadius = 4f),
                            textAlign = TextAlign.Center
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun BannerShimmer(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraSmall)
                .shimmerEffect(),
            text = "Media title",
            style = TextStyle(
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                lineHeight = MaterialTheme.typography.headlineLarge.lineHeight,
                letterSpacing = MaterialTheme.typography.headlineLarge.letterSpacing,
                fontWeight = FontWeight.SemiBold,
                color = Color.Transparent
            )
        )
    }
}
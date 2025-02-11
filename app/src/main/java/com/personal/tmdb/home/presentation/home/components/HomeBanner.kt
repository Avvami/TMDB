package com.personal.tmdb.home.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.personal.tmdb.core.navigation.Route
import com.personal.tmdb.core.domain.util.C
import com.personal.tmdb.core.domain.util.MediaType
import com.personal.tmdb.core.domain.util.shimmerEffect
import com.personal.tmdb.home.presentation.home.HomeState
import com.personal.tmdb.home.presentation.home.HomeUiEvent
import com.personal.tmdb.ui.theme.surfaceContainerDark

@Composable
fun HomeBanner(
    modifier: Modifier = Modifier,
    homeState: () -> HomeState,
    homeUiEvent: (HomeUiEvent) -> Unit
) {
    Box(
        modifier = modifier
            .clickable(
                interactionSource = null,
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
                            homeUiEvent(HomeUiEvent.OnNavigateTo(Route.Lost))
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
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.scrim.copy(.1f), BlendMode.Darken)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, surfaceContainerDark.copy(alpha = .65f)),
                        startY = 500f
                    )
                )
        )
        AsyncImage(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomCenter)
                .sizeIn(maxWidth = 300.dp, maxHeight = 85.dp)
                .fillMaxSize(),
            model = C.TMDB_IMAGES_BASE_URL + C.LOGO_W500 + (homeState().randomMediaLogos?.find { it?.iso6391 == "en" }?.filePath
                ?: homeState().randomMediaLogos?.getOrNull(0)?.filePath),
            contentDescription = "Logo",
            contentScale = ContentScale.Inside
        )
        /*TODO: Return title placeholder when image builder will be ready*/
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
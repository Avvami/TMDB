package com.personal.tmdb.detail.presentation.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.core.navigation.Route
import com.personal.tmdb.core.presentation.components.MediaCarousel
import com.personal.tmdb.core.domain.util.C
import com.personal.tmdb.core.domain.util.MediaType
import com.personal.tmdb.detail.data.models.Images
import com.personal.tmdb.detail.domain.util.ImageType
import com.personal.tmdb.detail.presentation.detail.DetailUiEvent
import com.personal.tmdb.ui.theme.surfaceLight

@Composable
fun DetailMedia(
    images: () -> Images,
    mediaId: () -> Int,
    mediaType: () -> MediaType,
    detailUiEvent: (DetailUiEvent) -> Unit
) {
    MediaCarousel(
        titleContent = {
            Text(text = stringResource(id = R.string.media))
        },
        items = {
            images().posters?.takeIf { it.isNotEmpty() }?.let { posters ->
                item {
                    Box(
                        modifier = Modifier
                            .height(170.dp)
                            .aspectRatio(2 / 3f)
                            .clip(MaterialTheme.shapes.medium)
                            .clickable {
                                detailUiEvent(
                                    DetailUiEvent.OnNavigateTo(
                                        Route.Image(
                                            imageType = ImageType.POSTERS.name.lowercase(),
                                            imagesPath = C.MEDIA_IMAGES.format(mediaType().name.lowercase(), mediaId()),
                                        )
                                    )
                                )
                            }
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .matchParentSize(),
                            model = C.TMDB_IMAGES_BASE_URL + C.POSTER_W300 + posters[0]?.filePath,
                            contentDescription = "Poster",
                            placeholder = painterResource(id = R.drawable.placeholder),
                            error = painterResource(id = R.drawable.placeholder),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, MaterialTheme.colorScheme.scrim.copy(.7f)),
                                        startY = 100f
                                    )
                                )
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(8.dp),
                                text = if (posters.size == 1) "${posters.size} Poster" else "${posters.size} Posters",
                                style = MaterialTheme.typography.labelLarge,
                                color = surfaceLight
                            )
                        }
                    }
                }
            }
            images().backdrops?.takeIf { it.isNotEmpty() }?.let { backdrops ->
                item {
                    Box(
                        modifier = Modifier
                            .height(170.dp)
                            .aspectRatio(16 / 9f)
                            .clip(MaterialTheme.shapes.medium)
                            .clickable {
                                detailUiEvent(
                                    DetailUiEvent.OnNavigateTo(
                                        Route.Image(
                                            imageType = ImageType.BACKDROPS.name.lowercase(),
                                            imagesPath = C.MEDIA_IMAGES.format(mediaType().name.lowercase(), mediaId()),
                                        )
                                    )
                                )
                            }
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .matchParentSize(),
                            model = C.TMDB_IMAGES_BASE_URL + C.BACKDROP_W780 + backdrops[0]?.filePath,
                            contentDescription = "Backdrop",
                            placeholder = painterResource(id = R.drawable.placeholder),
                            error = painterResource(id = R.drawable.placeholder),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, MaterialTheme.colorScheme.scrim.copy(.7f)),
                                        startY = 100f
                                    )
                                )
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(8.dp),
                                text = if (backdrops.size == 1) "${backdrops.size} Backdrop" else "${backdrops.size} Backdrops",
                                style = MaterialTheme.typography.labelLarge,
                                color = surfaceLight
                            )
                        }
                    }
                }
            }
        }
    )
}
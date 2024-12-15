package com.personal.tmdb.detail.presentation.detail.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.core.navigation.RootNavGraph
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.MediaRowView
import com.personal.tmdb.core.util.C
import com.personal.tmdb.detail.data.models.Images
import com.personal.tmdb.detail.domain.util.ImageType
import com.personal.tmdb.ui.theme.backgroundLight
import com.personal.tmdb.ui.theme.onBackgroundLight

@Composable
fun Media(
    onNavigateTo: (route: String) -> Unit,
    preferencesState: State<PreferencesState>,
    images: Images,
    mediaId: Int,
    mediaType: String
) {
    MediaRowView(
        titleRes = R.string.media,
        titleFontSize = 20.sp,
        items = {
            images.posters?.let { posters ->
                if (posters.isNotEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .height(150.dp)
                                .aspectRatio(2 / 3f)
                                .clip(RoundedCornerShape(preferencesState.value.corners.dp))
                                .clickable {
                                    onNavigateTo(RootNavGraph.IMAGE + "/${ImageType.POSTERS.name.lowercase()}" +
                                            "/${Uri.encode(C.MEDIA_IMAGES.format(mediaType, mediaId))}")
                                }
                        ) {
                            AsyncImage(
                                modifier = Modifier.fillMaxSize(),
                                model = C.TMDB_IMAGES_BASE_URL + C.POSTER_W300 + posters[0]?.filePath,
                                contentDescription = "Poster",
                                placeholder = painterResource(id = R.drawable.placeholder),
                                error = painterResource(id = R.drawable.placeholder),
                                contentScale = ContentScale.Crop
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Brush.verticalGradient(listOf(Color.Transparent, onBackgroundLight.copy(.7f))))
                            ) {
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(8.dp),
                                    text = "${posters.size} Posters",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = backgroundLight
                                )
                            }
                        }
                    }
                }
            }
            images.backdrops?.let { backdrops ->
                if (backdrops.isNotEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .height(150.dp)
                                .aspectRatio(16 / 9f)
                                .clip(RoundedCornerShape(preferencesState.value.corners.dp))
                                .clickable {
                                    onNavigateTo(RootNavGraph.IMAGE + "/${ImageType.BACKDROPS.name.lowercase()}" +
                                            "/${Uri.encode(C.MEDIA_IMAGES.format(mediaType, mediaId))}")
                                }
                        ) {
                            AsyncImage(
                                modifier = Modifier.fillMaxSize(),
                                model = C.TMDB_IMAGES_BASE_URL + C.BACKDROP_W780 + backdrops[0]?.filePath,
                                contentDescription = "Backdrop",
                                placeholder = painterResource(id = R.drawable.placeholder),
                                error = painterResource(id = R.drawable.placeholder),
                                contentScale = ContentScale.Crop
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Brush.verticalGradient(listOf(Color.Transparent, onBackgroundLight.copy(.7f))))
                            ) {
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(8.dp),
                                    text = "${backdrops.size} Backdrops",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = backgroundLight
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}
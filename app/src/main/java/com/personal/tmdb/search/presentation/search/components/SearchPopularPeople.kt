package com.personal.tmdb.search.presentation.search.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.core.presentation.MediaState
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.MediaPoster
import com.personal.tmdb.core.presentation.components.MediaPosterShimmer
import com.personal.tmdb.core.presentation.components.MediaRowView
import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.core.util.shimmerEffect

@Composable
fun SearchPopularPeople(
    onNavigateTo: (route: String) -> Unit,
    popularState: () -> MediaState,
    preferencesState: State<PreferencesState>,
) {
    MediaRowView(
        titleRes = R.string.popular_people,
        items = {
            if (popularState().isLoading) {
                items(15) {
                    MediaPosterShimmer(
                        modifier = Modifier
                            .height(150.dp)
                            .aspectRatio(0.675f)
                            .clip(MaterialTheme.shapes.large)
                            .shimmerEffect(),
                        showTitle = true
                    )
                }
            } else {
                popularState().mediaResponseInfo?.results?.let { popular ->
                    items(
                        count = popular.size,
                        key = { popular[it].id }
                    ) { index ->
                        val mediaInfo = popular[index]
                        MediaPoster(
                            modifier = Modifier
                                .height(150.dp)
                                .aspectRatio(0.675f)
                                .clip(MaterialTheme.shapes.large),
                            onNavigateTo = onNavigateTo,
                            mediaInfo = mediaInfo,
                            mediaType = MediaType.PERSON,
                            showTitle = preferencesState.value.showTitle,
                            showVoteAverage = preferencesState.value.showVoteAverage,
                            corners = preferencesState.value.corners
                        )
                    }
                }
            }
        }
    )
}
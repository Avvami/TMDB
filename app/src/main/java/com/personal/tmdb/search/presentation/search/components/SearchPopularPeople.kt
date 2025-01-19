package com.personal.tmdb.search.presentation.search.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.personal.tmdb.R
import com.personal.tmdb.core.presentation.MediaState
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.MediaPoster
import com.personal.tmdb.core.presentation.components.MediaPosterShimmer
import com.personal.tmdb.core.presentation.components.MediaRowView
import com.personal.tmdb.core.util.MediaType

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
                    MediaPosterShimmer(showTitle = preferencesState.value.showTitle)
                }
            } else {
                popularState().mediaResponseInfo?.results?.let { popular ->
                    items(
                        count = popular.size,
                        key = { popular[it].id }
                    ) { index ->
                        val mediaInfo = popular[index]
                        MediaPoster(
                            onNavigateTo = onNavigateTo,
                            mediaInfo = mediaInfo,
                            mediaType = MediaType.PERSON,
                            showTitle = preferencesState.value.showTitle,
                            showVoteAverage = preferencesState.value.showVoteAverage,
                        )
                    }
                }
            }
        }
    )
}
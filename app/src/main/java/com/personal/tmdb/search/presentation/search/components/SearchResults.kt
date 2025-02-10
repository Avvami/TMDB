package com.personal.tmdb.search.presentation.search.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.core.presentation.MediaState
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.MediaGrid
import com.personal.tmdb.core.presentation.components.MediaPoster
import com.personal.tmdb.core.presentation.components.MediaPosterShimmer
import com.personal.tmdb.core.domain.util.MediaType
import com.personal.tmdb.core.domain.util.negativeHorizontalPadding
import com.personal.tmdb.search.presentation.search.SearchUiEvent

@Composable
fun SearchResults(
    lazyGridState: LazyGridState,
    searchState: () -> MediaState,
    mediaType: () -> MediaType,
    preferencesState: State<PreferencesState>,
    searchUiEvent: (SearchUiEvent) -> Unit
) {
    MediaGrid(
        lazyGridState = lazyGridState,
        contentPadding = PaddingValues(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp),
        span = {
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                if (searchState().loading && searchState().mediaResponseInfo == null) {
                    SearchFilterChipsShimmer(
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                } else {
                    SearchFilterChips(
                        modifier = Modifier
                            .negativeHorizontalPadding((-16).dp)
                            .padding(bottom = 8.dp)
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp),
                        mediaType = mediaType,
                        searchUiEvent = searchUiEvent
                    )
                }
            }
        },
        items = {
            if (searchState().loading) {
                items(count = 15) {
                    MediaPosterShimmer(
                        modifier = Modifier.fillMaxWidth(),
                        height = Dp.Unspecified,
                        showTitle = preferencesState.value.showTitle
                    )
                }
            } else {
                searchState().mediaResponseInfo?.results?.let { results ->
                    if (results.isEmpty()) {
                        item(
                            span = { GridItemSpan(maxLineSpan) }
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = stringResource(id = R.string.empty_search),
                                    style = MaterialTheme.typography.titleMedium,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = stringResource(id = R.string.empty_search_suggest),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    } else {
                        items(
                            items = results,
                            key = { it.id },
                            contentType = { "Poster" }
                        ) { mediaInfo ->
                            MediaPoster(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateItem(),
                                onNavigateTo = { searchUiEvent(SearchUiEvent.OnNavigateTo(it)) },
                                height = Dp.Unspecified,
                                mediaInfo = mediaInfo,
                                mediaType = mediaInfo.mediaType ?: mediaType(),
                                showTitle = preferencesState.value.showTitle,
                                showVoteAverage = preferencesState.value.showVoteAverage
                            )
                        }
                    }
                }
            }
        }
    )
}
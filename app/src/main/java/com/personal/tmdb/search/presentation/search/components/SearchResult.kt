package com.personal.tmdb.search.presentation.search.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.compose.ui.unit.sp
import com.personal.tmdb.R
import com.personal.tmdb.core.presentation.MediaState
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.MediaListView
import com.personal.tmdb.core.presentation.components.MediaListViewShimmer
import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.core.util.shimmerEffect
import com.personal.tmdb.search.presentation.search.SearchUiEvent

@Composable
fun SearchResult(
    searchState: () -> MediaState,
    mediaType: MediaType,
    onNavigateTo: (route: String) -> Unit,
    preferencesState: State<PreferencesState>,
    searchUiEvent: (SearchUiEvent) -> Unit
) {
    if (searchState().isLoading && searchState().mediaResponseInfo == null) {
        MediaListViewShimmer(
            contentPadding = PaddingValues(16.dp),
            preferencesState = preferencesState,
            topItemContent = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .clip(MaterialTheme.shapes.small)
                        .shimmerEffect()
                        .padding(16.dp, 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.all),
                        color = Color.Transparent
                    )
                    Text(
                        text = stringResource(id = R.string.movies),
                        color = Color.Transparent
                    )
                    Text(
                        text = stringResource(id = R.string.people),
                        color = Color.Transparent
                    )
                }
            }
        )
    } else {
        searchState().mediaResponseInfo?.results?.let { results ->
            MediaListView(
                contentPadding = PaddingValues(16.dp),
                onNavigateTo = onNavigateTo,
                mediaList = { results },
                mediaType = mediaType,
                isLoading = { searchState().isLoading },
                preferencesState = preferencesState,
                emptyListContent = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.empty_search),
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.empty_search_suggest),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                },
                topItemContent = {
                    val sidePadding = (-16).dp
                    Row(
                        modifier = Modifier
                            .layout { measurable, constraints ->
                                val placeable =
                                    measurable.measure(constraints.offset(horizontal = -sidePadding.roundToPx() * 2))
                                layout(
                                    width = placeable.width + sidePadding.roundToPx() * 2,
                                    height = placeable.height
                                ) {
                                    placeable.place(+sidePadding.roundToPx(), 0)
                                }
                            }
                            .padding(bottom = 8.dp)
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FilterChip(
                            selected = mediaType == MediaType.MULTI,
                            onClick = { searchUiEvent(SearchUiEvent.SetSearchType(MediaType.MULTI.name.lowercase())) },
                            label = { Text(text = stringResource(id = R.string.all)) },
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                labelColor = MaterialTheme.colorScheme.surfaceVariant,
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            border = null
                        )
                        FilterChip(
                            selected = mediaType == MediaType.TV,
                            onClick = { searchUiEvent(SearchUiEvent.SetSearchType(MediaType.TV.name.lowercase())) },
                            label = { Text(text = stringResource(id = R.string.tv_shows)) },
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                labelColor = MaterialTheme.colorScheme.surfaceVariant,
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            border = null
                        )
                        FilterChip(
                            selected = mediaType == MediaType.MOVIE,
                            onClick = { searchUiEvent(SearchUiEvent.SetSearchType(MediaType.MOVIE.name.lowercase())) },
                            label = { Text(text = stringResource(id = R.string.movies)) },
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                labelColor = MaterialTheme.colorScheme.surfaceVariant,
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            border = null
                        )
                        FilterChip(
                            selected = mediaType == MediaType.PERSON,
                            onClick = { searchUiEvent(SearchUiEvent.SetSearchType(MediaType.PERSON.name.lowercase())) },
                            label = { Text(text = stringResource(id = R.string.people)) },
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                labelColor = MaterialTheme.colorScheme.surfaceVariant,
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            border = null
                        )
                    }
                }
            )
        }
    }
}
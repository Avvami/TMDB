package com.personal.tmdb.search.presentation.search.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.tmdb.R
import com.personal.tmdb.core.presentation.components.MediaPoster
import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.search.presentation.search.SearchState
import com.personal.tmdb.search.presentation.search.SearchUiEvent

@Composable
fun SearchResult(
    modifier: Modifier = Modifier,
    searchState: () -> SearchState,
    mediaType: MediaType,
    onNavigateTo: (route: String) -> Unit,
    showTitle: Boolean,
    showVoteAverage: Boolean,
    searchUiEvent: (SearchUiEvent) -> Unit
) {
    searchState().searchInfo?.results?.let { results ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(100.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_page_info_fill0_wght400),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    FilterChip(
                        selected = mediaType == MediaType.MULTI,
                        onClick = { searchUiEvent(SearchUiEvent.SetSearchType(MediaType.MULTI.name.lowercase())) },
                        label = { Text(text = stringResource(id = R.string.all)) }
                    )
                    FilterChip(
                        selected = mediaType == MediaType.TV,
                        onClick = { searchUiEvent(SearchUiEvent.SetSearchType(MediaType.TV.name.lowercase())) },
                        label = { Text(text = stringResource(id = R.string.tv_shows)) }
                    )
                    FilterChip(
                        selected = mediaType == MediaType.MOVIE,
                        onClick = { searchUiEvent(SearchUiEvent.SetSearchType(MediaType.MOVIE.name.lowercase())) },
                        label = { Text(text = stringResource(id = R.string.movies)) }
                    )
                    FilterChip(
                        selected = mediaType == MediaType.PERSON,
                        onClick = { searchUiEvent(SearchUiEvent.SetSearchType(MediaType.PERSON.name.lowercase())) },
                        label = { Text(text = stringResource(id = R.string.people)) }
                    )
                }
            }
            if (results.isNotEmpty()) {
                items(
                    count = results.size,
                    key = { results[it].id }
                ) { index ->
                    val mediaInfo = results[index]
                    MediaPoster(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(0.675f)
                            .clip(RoundedCornerShape(18.dp)),
                        onNavigateTo = onNavigateTo,
                        mediaInfo = mediaInfo,
                        mediaType = mediaType,
                        showTitle = showTitle,
                        showVoteAverage = showVoteAverage
                    )
                }
            } else {
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
                }
            }
        }
    }
}
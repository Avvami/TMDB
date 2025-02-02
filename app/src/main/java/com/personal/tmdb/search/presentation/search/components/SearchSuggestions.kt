package com.personal.tmdb.search.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.personal.tmdb.core.presentation.MediaState
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.search.presentation.search.SearchUiEvent

@Composable
fun SearchSuggestion(
    modifier: Modifier = Modifier,
    trendingState: () -> MediaState,
    popularPeopleState: () -> MediaState,
    preferencesState: State<PreferencesState>,
    searchUiEvent: (SearchUiEvent) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            SearchTrending(
                trendingState = trendingState,
                searchUiEvent = searchUiEvent
            )
        }
        item {
            SearchPopularPeople(
                popularState = popularPeopleState,
                preferencesState = preferencesState,
                searchUiEvent = searchUiEvent
            )
        }
    }
}
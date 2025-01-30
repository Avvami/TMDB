package com.personal.tmdb.search.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.personal.tmdb.core.navigation.Route
import com.personal.tmdb.core.presentation.MediaState
import com.personal.tmdb.core.presentation.PreferencesState

@Composable
fun SearchSuggestion(
    modifier: Modifier = Modifier,
    onNavigateTo: (route: Route) -> Unit,
    trendingState: () -> MediaState,
    popularPeopleState: () -> MediaState,
    preferencesState: State<PreferencesState>
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            SearchTrending(
                onNavigateTo = onNavigateTo,
                trendingState = trendingState
            )
        }
        item {
            SearchPopularPeople(
                onNavigateTo = onNavigateTo,
                popularState = popularPeopleState,
                preferencesState = preferencesState
            )
        }
    }
}
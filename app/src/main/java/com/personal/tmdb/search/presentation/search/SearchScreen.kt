package com.personal.tmdb.search.presentation.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.personal.tmdb.core.navigation.Route
import com.personal.tmdb.core.presentation.MediaState
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.util.convertMediaType
import com.personal.tmdb.search.presentation.search.components.SearchField
import com.personal.tmdb.search.presentation.search.components.SearchResults
import com.personal.tmdb.search.presentation.search.components.SearchSuggestion

@Composable
fun SearchScreenRoot(
    bottomPadding: Dp,
    lazyGridState: LazyGridState = rememberLazyGridState(),
    onNavigateTo: (route: Route) -> Unit,
    preferencesState: State<PreferencesState>,
    viewModel: SearchViewModel = hiltViewModel()
) {
    SearchScreen(
        modifier = Modifier.padding(bottom = bottomPadding),
        lazyGridState = lazyGridState,
        preferencesState = preferencesState,
        searchQuery = viewModel::searchQuery,
        searchType = viewModel::searchType,
        searchState = viewModel::searchState,
        trendingState = viewModel::trendingState,
        popularPeopleState = viewModel::popularState,
        searchUiEvent = { event ->
            when (event) {
                is SearchUiEvent.OnNavigateTo -> {
                    println(event.route)
                    onNavigateTo(event.route)
                }
                else -> Unit
            }
            viewModel.searchUiEvent(event)
        }
    )
}

@Composable
private fun SearchScreen(
    modifier: Modifier = Modifier,
    lazyGridState: LazyGridState,
    preferencesState: State<PreferencesState>,
    searchQuery: () -> String,
    searchType: () -> String,
    searchState: () -> MediaState,
    trendingState: () -> MediaState,
    popularPeopleState: () -> MediaState,
    searchUiEvent: (SearchUiEvent) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(top = innerPadding.calculateTopPadding()),
        ) {
            SearchField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                searchQuery = searchQuery,
                searchUiEvent = searchUiEvent
            )
            AnimatedContent(
                targetState = searchState().mediaResponseInfo == null && !searchState().isLoading && searchState().error == null,
                label = "Search screen content animation"
            ) { targetState ->
                if (targetState) {
                    SearchSuggestion(
                        trendingState = trendingState,
                        popularPeopleState = popularPeopleState,
                        preferencesState = preferencesState,
                        searchUiEvent = searchUiEvent
                    )
                } else {
                    SearchResults(
                        lazyGridState = lazyGridState,
                        searchState = searchState,
                        mediaType = { convertMediaType(searchType()) },
                        preferencesState = preferencesState,
                        searchUiEvent = searchUiEvent
                    )
                }
            }
        }
    }
}
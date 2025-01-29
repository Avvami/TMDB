package com.personal.tmdb.search.presentation.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.personal.tmdb.R
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.util.convertMediaType
import com.personal.tmdb.search.presentation.search.components.SearchPopularPeople
import com.personal.tmdb.search.presentation.search.components.SearchResult
import com.personal.tmdb.search.presentation.search.components.SearchTrending

@Composable
fun SearchScreen(
    bottomPadding: Dp,
    lazyGridState: LazyGridState = rememberLazyGridState(),
    onNavigateTo: (route: String) -> Unit,
    preferencesState: State<PreferencesState>,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding() + 16.dp, bottom = bottomPadding),
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = searchViewModel.searchQuery,
                onValueChange = {
                    searchViewModel.searchUiEvent(
                        SearchUiEvent.OnSearchQueryChange(
                            query = it,
                            searchType = searchViewModel.searchType,
                            page = 1
                        )
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.search_placeholder),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Search"
                    )
                },
                trailingIcon = {
                    AnimatedVisibility(
                        visible = searchViewModel.searchQuery.isNotEmpty(),
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut() + fadeOut()
                    ) {
                        IconButton(
                            onClick = {
                                searchViewModel.searchUiEvent(
                                    SearchUiEvent.OnSearchQueryChange(
                                        query = "",
                                        searchType = searchViewModel.searchType,
                                        page = 1
                                    )
                                )
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Clear,
                                contentDescription = "Clear"
                            )
                        }
                    }
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    cursorColor = MaterialTheme.colorScheme.onSurface,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                shape = MaterialTheme.shapes.medium
            )
            AnimatedContent(
                targetState = searchViewModel.searchState.mediaResponseInfo == null && !searchViewModel.searchState.isLoading && searchViewModel.searchState.error == null,
                label = "Search screen content animation"
            ) { targetState ->
                if (targetState) {
                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            SearchTrending(
                                onNavigateTo = onNavigateTo,
                                trendingState = searchViewModel::trendingState
                            )
                        }
                        item {
                            SearchPopularPeople(
                                onNavigateTo = onNavigateTo,
                                popularState = searchViewModel::popularState,
                                preferencesState = preferencesState
                            )
                        }
                    }
                } else {
                    SearchResult(
                        lazyGridState = lazyGridState,
                        searchState = searchViewModel::searchState,
                        mediaType = convertMediaType(searchViewModel.searchType),
                        onNavigateTo = onNavigateTo,
                        preferencesState = preferencesState,
                        searchUiEvent = searchViewModel::searchUiEvent
                    )
                }
            }
        }
    }
}
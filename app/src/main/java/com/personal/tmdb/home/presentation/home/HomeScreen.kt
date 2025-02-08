package com.personal.tmdb.home.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.personal.tmdb.R
import com.personal.tmdb.core.navigation.Route
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.MediaBanner
import com.personal.tmdb.core.presentation.components.MediaBannerShimmer
import com.personal.tmdb.core.presentation.components.MediaCarousel
import com.personal.tmdb.core.presentation.components.MediaPoster
import com.personal.tmdb.core.presentation.components.MediaPosterShimmer
import com.personal.tmdb.core.domain.util.MediaType
import com.personal.tmdb.home.presentation.home.components.HomeBanner

@Composable
fun HomeScreenRoot(
    bottomPadding: Dp,
    lazyListState: LazyListState = rememberLazyListState(),
    onNavigateTo: (route: Route) -> Unit,
    preferencesState: () -> PreferencesState,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeState by viewModel.homeState.collectAsStateWithLifecycle()
    HomeScreen(
        modifier = Modifier.padding(bottom = bottomPadding),
        lazyListState = lazyListState,
        preferencesState = preferencesState,
        homeState = { homeState },
        homeUiEvent = { event ->
            when (event) {
                is HomeUiEvent.OnNavigateTo -> {
                    onNavigateTo(event.route)
                }
                else -> Unit
            }
            viewModel.homeUiEvent(event)
        }
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    preferencesState: () -> PreferencesState,
    homeState: () -> HomeState,
    homeUiEvent: (HomeUiEvent) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(top = innerPadding.calculateTopPadding() + 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = lazyListState
        ) {
            item {
                CompositionLocalProvider(
                    LocalMinimumInteractiveComponentSize provides Dp.Unspecified
                ) {
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SuggestionChip(
                            onClick = { /*TODO*/ },
                            label = {
                                Text(text = stringResource(id = R.string.tv_shows))
                            },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                labelColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                            border = null
                        )
                        SuggestionChip(
                            onClick = { /*TODO*/ },
                            label = {
                                Text(text = stringResource(id = R.string.movies))
                            },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                labelColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                            border = null
                        )
                        SuggestionChip(
                            onClick = { /*TODO*/ },
                            label = {
                                Text(text = stringResource(id = R.string.people))
                            },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                labelColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                            border = null
                        )
                    }
                }
            }
            item {
                HomeBanner(
                    modifier = Modifier
                        .height(450.dp)
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .clip(MaterialTheme.shapes.extraLarge)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = .1f),
                            shape = MaterialTheme.shapes.extraLarge
                        ),
                    homeState = homeState,
                    homeUiEvent = homeUiEvent
                )
            }
            item {
                MediaCarousel(
                    titleContent = {
                        Text(text = stringResource(id = R.string.trending_today))
                    },
                    items = {
                        if (homeState().trending == null) {
                            items(15) {
                                MediaPosterShimmer(showTitle = preferencesState().showTitle)
                            }
                        } else {
                            homeState().trending?.results?.let { trending ->
                                items(
                                    items = trending,
                                    key = { it.id }
                                ) { mediaInfo ->
                                    MediaPoster(
                                        onNavigateTo = { homeUiEvent(HomeUiEvent.OnNavigateTo(it)) },
                                        mediaInfo = mediaInfo,
                                        mediaType = mediaInfo.mediaType,
                                        showTitle = preferencesState().showTitle,
                                        showVoteAverage = preferencesState().showVoteAverage,
                                    )
                                }
                            }
                        }
                    }
                )
            }
            item {
                MediaCarousel(
                    titleContent = {
                        Text(text = stringResource(id = R.string.now_playing))
                    },
                    items = {
                        if (homeState().nowPlaying == null) {
                            items(15) {
                                MediaBannerShimmer()
                            }
                        } else {
                            homeState().nowPlaying?.results?.let { nowPlaying ->
                                items(
                                    items = nowPlaying,
                                    key = { it.id }
                                ) { mediaInfo ->
                                    MediaBanner(
                                        onNavigateTo = { homeUiEvent(HomeUiEvent.OnNavigateTo(it)) },
                                        mediaInfo = mediaInfo,
                                        mediaType = MediaType.MOVIE,
                                        showVoteAverage = preferencesState().showVoteAverage
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface.copy(.7f))
                .statusBarsPadding()
        )
    }
}
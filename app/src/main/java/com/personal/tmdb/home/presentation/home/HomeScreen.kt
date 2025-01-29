package com.personal.tmdb.home.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.personal.tmdb.R
import com.personal.tmdb.UiEvent
import com.personal.tmdb.UserState
import com.personal.tmdb.core.navigation.RootNavGraph
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.MediaBanner
import com.personal.tmdb.core.presentation.components.MediaBannerShimmer
import com.personal.tmdb.core.presentation.components.MediaPoster
import com.personal.tmdb.core.presentation.components.MediaPosterShimmer
import com.personal.tmdb.core.presentation.components.MediaRowView
import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.core.util.shimmerEffect
import com.personal.tmdb.home.presentation.home.components.Banner
import com.personal.tmdb.ui.theme.surfaceLight
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    bottomPadding: Dp,
    lazyListState: LazyListState = rememberLazyListState(),
    onNavigateTo: (route: String) -> Unit,
    preferencesState: State<PreferencesState>,
    userState: State<UserState>,
    uiEvent: (UiEvent) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(key1 = userState.value.showSnackDone) {
        if (userState.value.showSnackDone) {
            scope.launch {
                homeViewModel.snackbarHostState.showSnackbar(
                    message = context.getString(R.string.signed_in_successfully)
                )
            }.also { uiEvent(UiEvent.DropSnackDone) }
        }
    }

    LaunchedEffect(key1 = userState.value.error) {
        if (userState.value.error != null) {
            scope.launch {
                homeViewModel.snackbarHostState.showSnackbar(
                    message = userState.value.error ?: "What happened??"
                )
            }.also { uiEvent(UiEvent.DropError) }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = homeViewModel.snackbarHostState)
        },
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(bottom = bottomPadding),
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
                            .padding(horizontal = 16.dp)
                            .zIndex(1f),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SuggestionChip(
                            onClick = { /*TODO*/ },
                            label = {
                                Text(text = stringResource(id = R.string.tv_shows))
                            },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .05f),
                                labelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .6f)
                            ),
                            border = null
                        )
                        SuggestionChip(
                            onClick = { /*TODO*/ },
                            label = {
                                Text(text = stringResource(id = R.string.movies))
                            },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .05f),
                                labelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .6f)
                            ),
                            border = null
                        )
                        SuggestionChip(
                            onClick = { /*TODO*/ },
                            label = {
                                Text(text = stringResource(id = R.string.people))
                            },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .05f),
                                labelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .6f)
                            ),
                            border = null
                        )
                    }
                }
            }
            item {
                Banner(
                    modifier = Modifier
                        .height(450.dp)
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .clip(MaterialTheme.shapes.extraLarge)
                        .border(
                            width = 2.dp,
                            color = surfaceLight.copy(alpha = .1f),
                            shape = MaterialTheme.shapes.extraLarge
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            homeViewModel.homeState.randomMedia?.let { randomMedia ->
                                when (randomMedia.mediaType) {
                                    MediaType.TV, MediaType.MOVIE -> {
                                        onNavigateTo(RootNavGraph.DETAIL + "/${randomMedia.mediaType.name.lowercase()}/${randomMedia.id}")
                                    }

                                    MediaType.PERSON -> {
                                        onNavigateTo(RootNavGraph.PERSON + "/${randomMedia.mediaType.name}/${randomMedia.id}")
                                    }

                                    else -> {
                                        /*TODO: Navigate to lost your way screen*/
                                    }
                                }
                            }
                        },
                    homeState = homeViewModel::homeState
                )
            }
            item {
                MediaRowView(
                    titleRes = R.string.trending,
                    items = {
                        if (homeViewModel.homeState.trending == null) {
                            items(15) {
                                MediaPosterShimmer(showTitle = preferencesState.value.showTitle)
                            }
                        } else {
                            homeViewModel.homeState.trending?.results?.let { trending ->
                                items(
                                    items = trending,
                                    key = { it.id }
                                ) { mediaInfo ->
                                    MediaPoster(
                                        onNavigateTo = onNavigateTo,
                                        mediaInfo = mediaInfo,
                                        mediaType = mediaInfo.mediaType,
                                        showTitle = preferencesState.value.showTitle,
                                        showVoteAverage = preferencesState.value.showVoteAverage,
                                    )
                                }
                            }
                        }
                    }
                )
            }
            item {
                MediaRowView(
                    titleRes = R.string.now_playing,
                    items = {
                        if (homeViewModel.homeState.nowPlaying == null) {
                            items(15) {
                                MediaBannerShimmer(
                                    modifier = Modifier
                                        .height(150.dp)
                                        .aspectRatio(16 / 9f)
                                        .clip(MaterialTheme.shapes.large)
                                        .shimmerEffect(),
                                    corners = preferencesState.value.corners
                                )
                            }
                        } else {
                            homeViewModel.homeState.nowPlaying?.results?.let { nowPlaying ->
                                items(
                                    items = nowPlaying,
                                    key = { it.id }
                                ) { mediaInfo ->
                                    MediaBanner(
                                        modifier = Modifier
                                            .height(180.dp)
                                            .aspectRatio(16 / 9f)
                                            .clip(MaterialTheme.shapes.large),
                                        onNavigateTo = onNavigateTo,
                                        mediaInfo = mediaInfo,
                                        mediaType = MediaType.MOVIE,
                                        showVoteAverage = preferencesState.value.showVoteAverage,
                                        corners = preferencesState.value.corners
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
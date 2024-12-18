package com.personal.tmdb.home.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
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
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.core.util.duotoneColorFilter
import com.personal.tmdb.core.util.shimmerEffect
import com.personal.tmdb.ui.theme.duotoneBlueDark
import com.personal.tmdb.ui.theme.duotoneBlueLight
import com.personal.tmdb.ui.theme.surfaceLight
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
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
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
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
            item {
                Box(
                    modifier = Modifier
                        .height(450.dp)
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .then(
                            if (!homeViewModel.homeState.randomMedia?.backdropPath.isNullOrEmpty()) {
                                Modifier.shadow(
                                    elevation = 32.dp,
                                    shape = MaterialTheme.shapes.extraLarge,
                                    ambientColor = duotoneBlueLight,
                                    spotColor = duotoneBlueLight
                                )
                            } else {
                                Modifier.shadow(
                                    elevation = 32.dp,
                                    shape = MaterialTheme.shapes.extraLarge
                                )
                            }
                        )
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .border(
                            width = 2.dp,
                            color = surfaceLight.copy(alpha = .1f),
                            shape = MaterialTheme.shapes.extraLarge
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = C.TMDB_IMAGES_BASE_URL + C.BACKDROP_W1280 + homeViewModel.homeState.randomMedia?.backdropPath,
                        contentDescription = "Backdrop",
                        contentScale = ContentScale.Crop,
                        colorFilter = duotoneColorFilter(duotoneBlueLight, duotoneBlueDark)
                    )
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterVertically),
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.welcome),
                                style = MaterialTheme.typography.displayMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = surfaceLight
                            )
                            Text(
                                text = stringResource(id = R.string.explore_now),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Medium,
                                color = surfaceLight
                            )
                        }
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = homeViewModel.searchQuery,
                            onValueChange = {
                                homeViewModel.homeUiEvent(HomeUiEvent.OnSearchQueryChange(it))
                            },
                            placeholder = {
                                Text(
                                    text = stringResource(id = R.string.search_placeholder),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            trailingIcon = {
                                FilledIconButton(
                                    onClick = {
                                        onNavigateTo(
                                            RootNavGraph.SEARCH +
                                                    "/${MediaType.MULTI.name.lowercase()}?${C.SEARCH_QUERY}=${homeViewModel.searchQuery}"
                                        )
                                    },
                                    colors = IconButtonDefaults.iconButtonColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = MaterialTheme.colorScheme.surface
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                                        contentDescription = "Search"
                                    )
                                }
                            },
                            singleLine = true,
                            shape = MaterialTheme.shapes.medium,
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedContainerColor = surfaceLight.copy(alpha = .4f),
                                focusedTextColor = surfaceLight,
                                unfocusedTextColor = surfaceLight,
                                focusedPlaceholderColor = surfaceLight,
                                unfocusedPlaceholderColor = surfaceLight,
                                focusedBorderColor = surfaceLight,
                                unfocusedBorderColor = Color.Transparent,
                                cursorColor = surfaceLight
                            ),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    onNavigateTo(
                                        RootNavGraph.SEARCH +
                                                "/${MediaType.MULTI.name.lowercase()}?${C.SEARCH_QUERY}=${homeViewModel.searchQuery}"
                                    )
                                }
                            )
                        )
                    }
                }
            }
            item {
                MediaRowView(
                    titleRes = R.string.trending,
                    items = {
                        if (homeViewModel.homeState.trending == null) {
                            items(15) {
                                MediaPosterShimmer(
                                    modifier = Modifier
                                        .height(150.dp)
                                        .aspectRatio(0.675f)
                                        .clip(MaterialTheme.shapes.large)
                                        .shimmerEffect(),
                                    showTitle = preferencesState.value.showTitle
                                )
                            }
                        } else {
                            homeViewModel.homeState.trending?.results?.let { trending ->
                                items(
                                    items = trending,
                                    key = { it.id }
                                ) { mediaInfo ->
                                    MediaPoster(
                                        modifier = Modifier
                                            .height(150.dp)
                                            .aspectRatio(0.675f)
                                            .clip(MaterialTheme.shapes.large),
                                        onNavigateTo = onNavigateTo,
                                        mediaInfo = mediaInfo,
                                        showTitle = preferencesState.value.showTitle,
                                        showVoteAverage = preferencesState.value.showVoteAverage,
                                        corners = preferencesState.value.corners
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
                                            .height(150.dp)
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
    }
}
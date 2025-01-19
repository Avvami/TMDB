package com.personal.tmdb.home.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
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
import com.personal.tmdb.core.util.shimmerEffect
import com.personal.tmdb.ui.theme.surfaceLight
import com.personal.tmdb.ui.theme.surfaceTintLight
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    bottomPadding: Dp,
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
                with(homeViewModel.homeState) {
                    Box(
                        modifier = Modifier
                            .height(450.dp)
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                            .shadow(
                                elevation = 28.dp,
                                shape = MaterialTheme.shapes.extraLarge,
                                ambientColor = MaterialTheme.colorScheme.surfaceTint,
                                spotColor = MaterialTheme.colorScheme.surfaceTint
                            )
                            .background(MaterialTheme.colorScheme.surface)
                            .border(
                                width = 2.dp,
                                color = surfaceLight.copy(alpha = .1f),
                                shape = MaterialTheme.shapes.extraLarge
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        val radialGradient by remember {
                            mutableStateOf(
                                object : ShaderBrush() {
                                    override fun createShader(size: Size): Shader {
                                        val biggerDimension = maxOf(size.height / 2, size.width / 2)
                                        return RadialGradientShader(
                                            colors = listOf(surfaceTintLight.copy(alpha = .8f), surfaceTintLight.copy(alpha = 0f)),
                                            center = Offset(0f, size.height),
                                            radius = biggerDimension
                                        )
                                    }
                                }
                            )
                        }
                        AsyncImage(
                            modifier = Modifier.fillMaxSize(),
                            model = C.TMDB_IMAGES_BASE_URL + C.BACKDROP_W1280 + randomMedia?.backdropPath,
                            contentDescription = "Backdrop",
                            contentScale = ContentScale.Crop,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.scrim.copy(.2f), BlendMode.Darken)
                        )
                        AnimatedVisibility(
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.BottomStart),
                            visible = randomMedia != null,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                            ) {
                                Column {
                                    randomMedia?.name?.let { name ->
                                        Text(
                                            text = name,
                                            style = TextStyle(
                                                fontSize = MaterialTheme.typography.displaySmall.fontSize,
                                                lineHeight = MaterialTheme.typography.displaySmall.lineHeight,
                                                letterSpacing = MaterialTheme.typography.displaySmall.letterSpacing,
                                                fontWeight = FontWeight.SemiBold,
                                                color = surfaceLight,
                                                shadow = Shadow(color = MaterialTheme.colorScheme.scrim.copy(alpha = .5f), offset = Offset(1f, 2f), blurRadius = 8f)
                                            ),
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    randomMedia?.overview?.let { overview ->
                                        Text(
                                            text = overview,
                                            style = TextStyle(
                                                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                                lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                                                letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
                                                color = surfaceLight.copy(alpha = .7f),
                                                shadow = Shadow(color = MaterialTheme.colorScheme.scrim.copy(alpha = .5f), offset = Offset(1f, 2f), blurRadius = 6f)
                                            ),
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CompositionLocalProvider(
                                        LocalMinimumInteractiveComponentSize provides Dp.Unspecified
                                    ) {
                                        Button(
                                            onClick = {
                                                when (randomMedia?.mediaType) {
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
                                            },
                                            shape = MaterialTheme.shapes.small,
                                            contentPadding = ButtonDefaults.ButtonWithIconContentPadding
                                        ) {
                                            Icon(
                                                modifier = Modifier.size(ButtonDefaults.IconSize),
                                                painter = painterResource(id = R.drawable.icon_info_fill0_wght400),
                                                contentDescription = "Info"
                                            )
                                            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                                            Text(text = stringResource(id = R.string.info))
                                        }
                                        FilledIconButton(
                                            onClick = { /*TODO*/ },
                                            colors = IconButtonDefaults.filledIconButtonColors(
                                                containerColor = surfaceLight.copy(alpha = .1f),
                                                contentColor = surfaceLight
                                            ),
                                            shape = MaterialTheme.shapes.small
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.icon_bookmark_fill0_wght400),
                                                contentDescription = "Add to Watchlist"
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
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
package com.personal.tmdb.detail.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.personal.tmdb.R
import com.personal.tmdb.UserState
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.CustomIconButton
import com.personal.tmdb.core.presentation.components.MediaPoster
import com.personal.tmdb.core.presentation.components.MediaRowView
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.shareText
import com.personal.tmdb.detail.presentation.detail.components.AllEpisodes
import com.personal.tmdb.detail.presentation.detail.components.DetailScreenShimmer
import com.personal.tmdb.detail.presentation.detail.components.Details
import com.personal.tmdb.detail.presentation.detail.components.Media
import com.personal.tmdb.detail.presentation.detail.components.Reviews
import com.personal.tmdb.detail.presentation.detail.components.Trailer
import com.personal.tmdb.ui.theme.backgroundLight
import com.personal.tmdb.ui.theme.tmdbDarkBlue
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navigateBack: () -> Unit,
    onNavigateTo: (route: String) -> Unit,
    navigateToHome: () -> Unit,
    preferencesState: State<PreferencesState>,
    userState: State<UserState>,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val hazeState = remember { HazeState() }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground
                ),
                navigationIcon = {
                    CustomIconButton(
                        onClick = navigateBack,
                        onLongClick = navigateToHome
                    )  {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                },
                actions = {
                    val context = LocalContext.current
                    IconButton(
                        onClick = {
                            context.shareText(C.SHARE_MEDIA.format(detailViewModel.mediaType, detailViewModel.mediaId))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Share,
                            contentDescription = "Share"
                        )
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.haze(state = hazeState, style = HazeDefaults.style(tmdbDarkBlue)),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 72.dp)
            ) {
                if (detailViewModel.detailState.isLoading) {
                    item {
                        DetailScreenShimmer()
                    }
                } else {
                    detailViewModel.detailState.error?.let {
                        item {
                            Box(
                                modifier = Modifier
                                    .height(200.dp)
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.surfaceContainerHigh),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    text = stringResource(id = R.string.tmdb),
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.outlineVariant,
                                    fontWeight = FontWeight.Medium,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    detailViewModel.detailState.mediaDetail?.let { info ->
                        item {
                            Trailer(
                                info = { info },
                                availableSearchQuery = detailViewModel.availableSearchQuery.collectAsStateWithLifecycle(),
                                availableCountries = detailViewModel.availableCountries.collectAsStateWithLifecycle(),
                                availableState = detailViewModel::availableState,
                                detailUiEvent = detailViewModel::detailUiEvent
                            )
                        }
                        item {
                            Details(
                                onNavigateTo = onNavigateTo,
                                mediaType = detailViewModel.detailState.mediaType,
                                info = { info },
                                collectionState = detailViewModel::collectionState,
                                isOverviewCollapsed = detailViewModel::isOverviewCollapsed,
                                showMore = detailViewModel::showMore,
                                preferencesState = preferencesState,
                                userState = userState,
                                detailUiEvent = detailViewModel::detailUiEvent
                            )
                        }
                        item {
                            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                        }
                        if (!info.seasons.isNullOrEmpty()) {
                            item {
                                AllEpisodes(
                                    onNavigateTo = onNavigateTo,
                                    info = info,
                                    seasons = info.seasons,
                                    preferencesState = preferencesState
                                )
                            }
                        }
                        info.reviews?.results?.let { reviews ->
                            if (reviews.isNotEmpty()) {
                                item {
                                    Reviews(
                                        onNavigateTo = onNavigateTo,
                                        preferencesState = preferencesState,
                                        reviews = reviews,
                                        mediaId = detailViewModel.mediaId,
                                        mediaType = detailViewModel.mediaType
                                    )
                                }
                            }
                        }
                        info.images?.let { images ->
                            if (!images.posters.isNullOrEmpty() || !images.backdrops.isNullOrEmpty()) {
                                item {
                                    Media(
                                        onNavigateTo = onNavigateTo,
                                        preferencesState = preferencesState,
                                        images = images,
                                        mediaId = detailViewModel.mediaId,
                                        mediaType = detailViewModel.mediaType
                                    )
                                }
                            }
                        }
                        info.similar?.results?.let { similar ->
                            if (similar.isNotEmpty()) {
                                item {
                                    MediaRowView(
                                        titleRes = R.string.similar,
                                        titleFontSize = 20.sp,
                                        items = {
                                            items(
                                                count = similar.size,
                                                key = { similar[it].id }
                                            ) { index ->
                                                val mediaInfo = similar[index]
                                                MediaPoster(
                                                    modifier = Modifier
                                                        .height(150.dp)
                                                        .aspectRatio(0.675f)
                                                        .clip(
                                                            RoundedCornerShape(
                                                                preferencesState.value.corners.dp
                                                            )
                                                        ),
                                                    onNavigateTo = onNavigateTo,
                                                    mediaInfo = mediaInfo,
                                                    mediaType = detailViewModel.detailState.mediaType,
                                                    showTitle = preferencesState.value.showTitle,
                                                    showVoteAverage = preferencesState.value.showVoteAverage,
                                                    corners = preferencesState.value.corners
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        }
                        info.recommendations?.results?.let { recommendations ->
                            if (recommendations.isNotEmpty()) {
                                item {
                                    MediaRowView(
                                        titleRes = R.string.recommendations,
                                        titleFontSize = 20.sp,
                                        items = {
                                            items(
                                                count = recommendations.size,
                                                key = { recommendations[it].id }
                                            ) { index ->
                                                val mediaInfo = recommendations[index]
                                                MediaPoster(
                                                    modifier = Modifier
                                                        .height(150.dp)
                                                        .aspectRatio(0.675f)
                                                        .clip(
                                                            RoundedCornerShape(
                                                                preferencesState.value.corners.dp
                                                            )
                                                        ),
                                                    onNavigateTo = onNavigateTo,
                                                    mediaInfo = mediaInfo,
                                                    mediaType = detailViewModel.detailState.mediaType,
                                                    showTitle = preferencesState.value.showTitle,
                                                    showVoteAverage = preferencesState.value.showVoteAverage,
                                                    corners = preferencesState.value.corners
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape((preferencesState.value.corners / 1.5).dp))
                    .clickable(interactionSource = null, indication = null) {}
                    .hazeChild(hazeState)
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = { /*TODO*/ },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = backgroundLight)
                ) {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_segment_fill0_wght400),
                            contentDescription = "Lists"
                        )
                    }
                }
                IconButton(
                    onClick = { /*TODO*/ },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = backgroundLight)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_bookmark_fill0_wght400),
                        contentDescription = "Watch Later"
                    )
                }
                IconButton(
                    onClick = { /*TODO*/ },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = backgroundLight)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_favorite_fill0_wght400),
                        contentDescription = "Favorite"
                    )
                }
                IconButton(
                    onClick = { /*TODO*/ },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = backgroundLight)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_thumbs_up_down_fill0_wght400),
                        contentDescription = "Rate"
                    )
                }
            }
        }
    }
}
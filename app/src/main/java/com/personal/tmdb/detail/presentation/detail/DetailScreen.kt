package com.personal.tmdb.detail.presentation.detail

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.personal.tmdb.R
import com.personal.tmdb.UserState
import com.personal.tmdb.core.navigation.Route
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.MediaCarousel
import com.personal.tmdb.core.presentation.components.MediaPoster
import com.personal.tmdb.core.domain.util.C
import com.personal.tmdb.core.domain.util.shareText
import com.personal.tmdb.detail.presentation.detail.components.DetailActionButtons
import com.personal.tmdb.detail.presentation.detail.components.DetailBanner
import com.personal.tmdb.detail.presentation.detail.components.DetailCollection
import com.personal.tmdb.detail.presentation.detail.components.DetailCredits
import com.personal.tmdb.detail.presentation.detail.components.DetailEpisodes
import com.personal.tmdb.detail.presentation.detail.components.DetailMedia
import com.personal.tmdb.detail.presentation.detail.components.DetailOverview
import com.personal.tmdb.detail.presentation.detail.components.DetailReview
import com.personal.tmdb.detail.presentation.detail.components.DetailScreenShimmer
import com.personal.tmdb.detail.presentation.detail.components.DetailTitle

@Composable
fun DetailScreenRoot(
    bottomPadding: Dp,
    onNavigateBack: () -> Unit,
    onNavigateTo: (route: Route) -> Unit,
    preferencesState: () -> PreferencesState,
    userState: () -> UserState,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val detailState by viewModel.detailState.collectAsStateWithLifecycle()
    DetailScreen(
        modifier = Modifier.padding(bottom = bottomPadding),
        preferencesState = preferencesState,
        userState = userState,
        detailState = { detailState },
        detailUiEvent = { event ->
            when (event) {
                DetailUiEvent.OnNavigateBack -> onNavigateBack()
                is DetailUiEvent.OnNavigateTo -> onNavigateTo(event.route)
                else -> Unit
            }
            viewModel.detailUiEvent(event)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailScreen(
    modifier: Modifier = Modifier,
    preferencesState: () -> PreferencesState,
    userState: () -> UserState,
    detailState: () -> DetailState,
    detailUiEvent: (DetailUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { detailUiEvent(DetailUiEvent.OnNavigateBack) }
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
                            context.shareText(C.SHARE_MEDIA.format(detailState().mediaType.name.lowercase(), detailState().mediaId))
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
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = innerPadding.calculateTopPadding(), bottom = 16.dp)
        ) {
            if (detailState().loading) {
                item {
                    DetailScreenShimmer()
                }
            } else {
                detailState().errorMessage?.let {
                    item { /*TODO: Display error*/ }
                }
                detailState().details?.let { info ->
                    item {
                        DetailTitle(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            info = { info },
                            userState = userState
                        )
                    }
                    item {
                        DetailBanner(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .clip(MaterialTheme.shapes.large)
                                .border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = .1f),
                                    shape = MaterialTheme.shapes.large
                                ),
                            info = { info },
                            watchCountry = detailState()::watchCountry,
                            detailUiEvent = detailUiEvent
                        )
                    }
                    info.overview?.let { overview ->
                        item {
                            DetailOverview(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth()
                                    .clickable(
                                        interactionSource = null,
                                        indication = null
                                    ) {
                                        /*TODO: Show more info*/
                                    },
                                overview = overview
                            )
                        }
                    }
                    item {
                        DetailCredits(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            info = { info },
                            mediaType = detailState()::mediaType,
                            detailUiEvent = detailUiEvent
                        )
                    }
                    item {
                        DetailActionButtons(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            info = { info },
                            userState = userState,
                            detailUiEvent = detailUiEvent
                        )
                    }
                    detailState().collection?.let { collectionInfo ->
                        item {
                            DetailCollection(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .animateItem()
                                    .height(IntrinsicSize.Min)
                                    .clip(MaterialTheme.shapes.medium)
                                    .clickable {
                                        detailUiEvent(
                                            DetailUiEvent.OnNavigateTo(
                                                Route.Collection(
                                                    collectionId = collectionInfo.id
                                                )
                                            )
                                        )
                                    }
                                    .border(
                                        width = 2.dp,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = .1f),
                                        shape = MaterialTheme.shapes.large
                                    ),
                                collectionInfo = { collectionInfo }
                            )
                        }
                    }
                    info.seasons?.takeIf { it.isNotEmpty() }?.let { seasons ->
                        item {
                            DetailEpisodes(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                info = info,
                                seasons = seasons,
                                detailUiEvent = detailUiEvent
                            )
                        }
                    }
                    info.reviews?.results?.takeIf { it.isNotEmpty() }?.let { reviews ->
                        item {
                            DetailReview(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                reviews = { reviews },
                                mediaId = detailState()::mediaId,
                                mediaType = detailState()::mediaType,
                                detailUiEvent = detailUiEvent
                            )
                        }
                    }
                    info.images?.let { images ->
                        if (!images.posters.isNullOrEmpty() || !images.backdrops.isNullOrEmpty()) {
                            item {
                                DetailMedia(
                                    images = { images },
                                    mediaId = detailState()::mediaId,
                                    mediaType = detailState()::mediaType,
                                    detailUiEvent = detailUiEvent
                                )
                            }
                        }
                    }
                    info.similar?.results?.takeIf { it.isNotEmpty() }?.let { similar ->
                        item {
                            MediaCarousel(
                                titleContent = {
                                    Text(text = stringResource(id = R.string.similar))
                                },
                                items = {
                                    items(
                                        items = similar,
                                        key = { it.id }
                                    ) { mediaInfo ->
                                        MediaPoster(
                                            onNavigateTo = { detailUiEvent(DetailUiEvent.OnNavigateTo(it)) },
                                            mediaInfo = mediaInfo,
                                            mediaType = mediaInfo.mediaType ?: detailState().mediaType,
                                            showTitle = preferencesState().showTitle,
                                            showVoteAverage = preferencesState().showVoteAverage,
                                        )
                                    }
                                }
                            )
                        }
                    }
                    info.recommendations?.results?.takeIf { it.isNotEmpty() }?.let { recommendations ->
                        item {
                            MediaCarousel(
                                titleContent = {
                                    Text(text = stringResource(id = R.string.recommendations))
                                },
                                items = {
                                    items(
                                        items = recommendations,
                                        key = { it.id }
                                    ) { mediaInfo ->
                                        MediaPoster(
                                            onNavigateTo = { detailUiEvent(DetailUiEvent.OnNavigateTo(it)) },
                                            mediaInfo = mediaInfo,
                                            mediaType = mediaInfo.mediaType ?: detailState().mediaType,
                                            showTitle = preferencesState().showTitle,
                                            showVoteAverage = preferencesState().showVoteAverage,
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
}
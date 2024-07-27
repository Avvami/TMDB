package com.personal.tmdb.detail.presentation.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.personal.tmdb.R
import com.personal.tmdb.core.presentation.components.MediaPoster
import com.personal.tmdb.detail.presentation.detail.components.DetailScreenShimmer
import com.personal.tmdb.detail.presentation.detail.components.Details
import com.personal.tmdb.detail.presentation.detail.components.Episodes
import com.personal.tmdb.detail.presentation.detail.components.Trailer

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(
    navigateBack: () -> Unit,
    onNavigateTo: (route: String) -> Unit,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
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
                    IconButton(
                        onClick = navigateBack
                    )  {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
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
                            detailUiEvent = detailViewModel::detailUiEvent
                        )
                    }
                    item {
                        detailViewModel.pagerPageCount?.let { pageCount ->
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                val horizontalPagerState = rememberPagerState(
                                    initialPage = detailViewModel.selectedTab,
                                    pageCount = { pageCount }
                                )
                                LaunchedEffect(key1 = detailViewModel.selectedTab) {
                                    horizontalPagerState.animateScrollToPage(detailViewModel.selectedTab)
                                }
                                ScrollableTabRow(
                                    selectedTabIndex = detailViewModel.selectedTab,
                                    containerColor = Color.Transparent,
                                    edgePadding = 16.dp
                                ) {
                                    detailViewModel.pageLabelsRes.fastForEachIndexed { index, stringRes ->
                                        Tab(
                                            selected = index == detailViewModel.selectedTab,
                                            onClick = {
                                                detailViewModel.detailUiEvent(DetailUiEvent.SetSelectedTab(index))
                                            },
                                            text = {
                                                Text(text = stringResource(id = stringRes))
                                            }
                                        )
                                    }
                                }
                                HorizontalPager(
                                    modifier = Modifier.fillMaxWidth(),
                                    state = horizontalPagerState,
                                    verticalAlignment = Alignment.Top,
                                    userScrollEnabled = false
                                ) { page ->
                                    when (detailViewModel.pageLabelsRes[page]) {
                                        R.string.episodes -> {
                                            info.seasons?.let { seasons ->
                                                Episodes(
                                                    onNavigateTo = onNavigateTo,
                                                    seasonState = detailViewModel::seasonState,
                                                    seasons = { seasons },
                                                    detailInfo = { info },
                                                    selectedSeasonNumber = detailViewModel::selectedSeasonNumber,
                                                    isSeasonDropdownExpanded = detailViewModel::isSeasonDropdownExpanded,
                                                    isSeasonOverviewCollapsed = detailViewModel::isSeasonOverviewCollapsed,
                                                    detailUiEvent = detailViewModel::detailUiEvent
                                                )
                                            }
                                        }
                                        R.string.recommendations -> {
                                            info.recommendations?.results?.let { recommendations ->
                                                if (recommendations.isEmpty()) {
                                                    Text(
                                                        modifier = Modifier
                                                            .padding(vertical = 8.dp)
                                                            .fillMaxWidth(),
                                                        text = stringResource(id = R.string.no_recommendations, info.name ?: ""),
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                        textAlign = TextAlign.Center
                                                    )
                                                } else {
                                                    LazyRow(
                                                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                                    ) {
                                                        items(
                                                            count = recommendations.size,
                                                            key = { recommendations[it].id }
                                                        ) { index ->
                                                            val mediaInfo = recommendations[index]
                                                            MediaPoster(
                                                                modifier = Modifier
                                                                    .height(150.dp)
                                                                    .aspectRatio(0.675f)
                                                                    .clip(RoundedCornerShape(18.dp)),
                                                                onNavigateTo = onNavigateTo,
                                                                mediaInfo = mediaInfo,
                                                                showTitle = true,
                                                                showVoteAverage = true
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        R.string.similar -> {
                                            info.similar?.results?.let { similar ->
                                                if (similar.isEmpty()) {
                                                    Text(
                                                        modifier = Modifier
                                                            .padding(vertical = 8.dp)
                                                            .fillMaxWidth(),
                                                        text = stringResource(id = R.string.no_similar),
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                        textAlign = TextAlign.Center
                                                    )
                                                } else {
                                                    LazyRow(
                                                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                                    ) {
                                                        items(
                                                            count = similar.size,
                                                            key = { similar[it].id }
                                                        ) { index ->
                                                            val mediaInfo = similar[index]
                                                            MediaPoster(
                                                                modifier = Modifier
                                                                    .height(150.dp)
                                                                    .aspectRatio(0.675f)
                                                                    .clip(RoundedCornerShape(18.dp)),
                                                                onNavigateTo = onNavigateTo,
                                                                mediaInfo = mediaInfo,
                                                                mediaType = detailViewModel.detailState.mediaType,
                                                                showTitle = true,
                                                                showVoteAverage = true
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
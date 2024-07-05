package com.personal.tmdb.core.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.personal.tmdb.core.domain.models.MediaInfo
import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.core.util.shimmerEffect

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MediaListView(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    onNavigateTo: (route: String) -> Unit,
    topItemContent: @Composable (() -> Unit)? = null,
    mediaList: () -> List<MediaInfo>,
    mediaType: MediaType? = null,
    emptyListContent: @Composable (() -> Unit)? = null,
    useCards: () -> Boolean,
    showTitle: () -> Boolean,
    showVoteAverage: () -> Boolean
) {
    AnimatedContent(
        targetState = useCards(),
        label = ""
    ) { targetState ->
        if (targetState) {
            LazyColumn(
                modifier = modifier,
                contentPadding = contentPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                topItemContent?.let { content ->
                    item {
                        content()
                    }
                }
                if (mediaList().isEmpty()) {
                    emptyListContent?.let { content ->
                        item {
                            content()
                        }
                    }
                } else {
                    items(
                        count = mediaList().size,
                        key = { mediaList()[it].id }
                    ) { index ->
                        val mediaInfo = mediaList()[index]
                        MediaCard(
                            modifier = Modifier.animateItemPlacement(),
                            onNavigateTo = onNavigateTo,
                            mediaInfo = mediaInfo,
                            mediaType = mediaType,
                            showVoteAverage = showVoteAverage()
                        )
                    }
                }
            }
        } else {
            LazyVerticalGrid(
                modifier = modifier,
                columns = GridCells.Adaptive(100.dp),
                contentPadding = contentPadding,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                topItemContent?.let { content ->
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        content()
                    }
                }
                if (mediaList().isEmpty()) {
                    emptyListContent?.let { content ->
                        item(
                            span = { GridItemSpan(maxLineSpan) }
                        ) {
                            content()
                        }
                    }
                } else {
                    items(
                        count = mediaList().size,
                        key = { mediaList()[it].id }
                    ) { index ->
                        val mediaInfo = mediaList()[index]
                        MediaPoster(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(0.675f)
                                .clip(RoundedCornerShape(18.dp))
                                .animateItemPlacement(),
                            onNavigateTo = onNavigateTo,
                            mediaInfo = mediaInfo,
                            mediaType = mediaType,
                            showTitle = showTitle(),
                            showVoteAverage = showVoteAverage()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MediaListViewShimmer(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    topItemContent: @Composable (() -> Unit)? = null,
    useCards: () -> Boolean,
    showTitle: () -> Boolean
) {
    AnimatedContent(
        targetState = useCards(),
        label = ""
    ) { targetState ->
        if (targetState) {
            LazyColumn(
                modifier = modifier,
                contentPadding = contentPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                topItemContent?.let { content ->
                    item {
                        content()
                    }
                }
                items(count = 15) {
                    MediaCardShimmer()
                }
            }
        } else {
            LazyVerticalGrid(
                modifier = modifier,
                columns = GridCells.Adaptive(100.dp),
                contentPadding = contentPadding,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                topItemContent?.let { content ->
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        content()
                    }
                }
                items(count = 15) {
                    MediaPosterShimmer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(0.675f)
                            .clip(RoundedCornerShape(18.dp))
                            .shimmerEffect(),
                        showTitle = showTitle()
                    )
                }
            }
        }
    }
}
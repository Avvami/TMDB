package com.personal.tmdb.core.presentation.components

import androidx.annotation.StringRes
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.tmdb.core.domain.models.MediaInfo

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MediaListView(
    modifier: Modifier = Modifier,
    onNavigateTo: (route: String) -> Unit,
    topItemContent: @Composable (() -> Unit)? = null,
    mediaList: () -> List<MediaInfo>?,
    @StringRes emptyListTextRes: Int,
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
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                topItemContent?.let { content ->
                    item {
                        content()
                    }
                }
                mediaList()?.let { mediaList ->
                    if (mediaList.isEmpty()) {
                        item {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(id = emptyListTextRes),
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        items(
                            count = mediaList.size,
                            key = { mediaList[it].id }
                        ) { index ->
                            val mediaInfo = mediaList[index]
                            MediaCard(
                                modifier = Modifier.animateItemPlacement(),
                                onNavigateTo = onNavigateTo,
                                mediaInfo = mediaInfo,
                                mediaType = mediaInfo.mediaType,
                                showVoteAverage = showVoteAverage()
                            )
                        }
                    }
                }
            }
        } else {
            LazyVerticalGrid(
                modifier = modifier,
                columns = GridCells.Adaptive(100.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
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
                mediaList()?.let { mediaList ->
                    if (mediaList.isEmpty()) {
                        item(
                            span = { GridItemSpan(maxLineSpan) }
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(id = emptyListTextRes),
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        items(
                            count = mediaList.size,
                            key = { mediaList[it].id }
                        ) { index ->
                            val mediaInfo = mediaList[index]
                            MediaPoster(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(0.675f)
                                    .clip(RoundedCornerShape(18.dp))
                                    .animateItemPlacement(),
                                onNavigateTo = onNavigateTo,
                                mediaInfo = mediaInfo,
                                mediaType = mediaInfo.mediaType,
                                showTitle = showTitle(),
                                showVoteAverage = showVoteAverage()
                            )
                        }
                    }
                }
            }
        }
    }
}
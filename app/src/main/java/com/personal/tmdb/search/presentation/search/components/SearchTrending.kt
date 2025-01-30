package com.personal.tmdb.search.presentation.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.personal.tmdb.R
import com.personal.tmdb.core.navigation.Route
import com.personal.tmdb.core.presentation.MediaState
import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.core.util.shimmerEffect

@Composable
fun SearchTrending(
    modifier: Modifier = Modifier,
    onNavigateTo: (route: Route) -> Unit,
    trendingState: () -> MediaState
) {
    if (trendingState().isLoading) {
        SearchTrendingShimmer(modifier)
    } else {
        trendingState().mediaResponseInfo?.results?.take(10)?.let { mediaInfoList ->
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(26.dp),
                        painter = painterResource(id = R.drawable.icon_trending_up_fill0_wght400),
                        contentDescription = "Trending"
                    )
                    Text(
                        text = stringResource(id = R.string.trending),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
                Column {
                    mediaInfoList.fastForEach { mediaInfo ->
                        mediaInfo.name?.let { name ->
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        mediaInfo.mediaType?.let { mediaType ->
                                            when(mediaType) {
                                                MediaType.TV, MediaType.MOVIE -> {
//                                                    onNavigateTo()
                                                }
                                                MediaType.PERSON -> {
//                                                    onNavigateTo()
                                                }
                                                else -> {
                                                    /*TODO: Navigate to lost your way screen*/
                                                }
                                            }
                                        }
                                    }
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                text = name,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchTrendingShimmer(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(26.dp),
                painter = painterResource(id = R.drawable.icon_trending_up_fill0_wght400),
                contentDescription = "Trending"
            )
            Text(
                text = stringResource(id = R.string.trending),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium
            )
        }
        Column {
            repeat(10) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .clip(MaterialTheme.shapes.extraSmall)
                        .shimmerEffect(),
                    text = "",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
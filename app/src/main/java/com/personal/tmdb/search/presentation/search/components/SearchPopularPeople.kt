package com.personal.tmdb.search.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.core.presentation.MediaState
import com.personal.tmdb.core.presentation.components.MediaPoster
import com.personal.tmdb.core.presentation.components.MediaPosterShimmer
import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.core.util.shimmerEffect

@Composable
fun SearchPopularPeople(
    onNavigateTo: (route: String) -> Unit,
    popularState: () -> MediaState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(id = R.string.popular_people),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (popularState().isLoading) {
                items(15) {
                    MediaPosterShimmer(
                        modifier = Modifier
                            .height(150.dp)
                            .aspectRatio(0.675f)
                            .clip(RoundedCornerShape(18.dp))
                            .shimmerEffect(),
                        showTitle = true
                    )
                }
            } else {
                popularState().mediaResponseInfo?.results?.let { popular ->
                    items(
                        count = popular.size,
                        key = { popular[it].id }
                    ) { index ->
                        val mediaInfo = popular[index]
                        MediaPoster(
                            modifier = Modifier
                                .height(150.dp)
                                .aspectRatio(0.675f)
                                .clip(RoundedCornerShape(18.dp)),
                            onNavigateTo = onNavigateTo,
                            mediaInfo = mediaInfo,
                            mediaType = MediaType.PERSON,
                            showTitle = true,
                            showVoteAverage = true
                        )
                    }
                }
            }
        }
    }
}
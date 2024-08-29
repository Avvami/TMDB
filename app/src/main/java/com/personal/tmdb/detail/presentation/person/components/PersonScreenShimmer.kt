package com.personal.tmdb.detail.presentation.person.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.personal.tmdb.R
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.MediaPosterShimmer
import com.personal.tmdb.core.util.shimmerEffect
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PersonScreenShimmer(
    preferencesState: State<PreferencesState>
) {
    val pageCount = 10
    val horizontalPagerState = rememberPagerState(
        pageCount = { pageCount },
        initialPage = pageCount / 2
    )
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        val contentPadding = (maxWidth - 150.dp) / 2
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            HorizontalPager(
                state = horizontalPagerState,
                contentPadding = PaddingValues(horizontal = contentPadding),
                pageSpacing = (maxWidth / 150) - 8.dp,
                flingBehavior = PagerDefaults.flingBehavior(
                    state = horizontalPagerState,
                    pagerSnapDistance = PagerSnapDistance.atMost(3)
                )
            ) { page ->
                Box(
                    modifier = Modifier
                        .height(230.dp)
                        .aspectRatio(0.675f)
                        .graphicsLayer {
                            val pageOffset =
                                ((horizontalPagerState.currentPage - page) + horizontalPagerState.currentPageOffsetFraction).absoluteValue
                            lerp(
                                start = .85f,
                                stop = 1f,
                                fraction = 1f - pageOffset.absoluteValue.coerceIn(
                                    0f,
                                    1f
                                ),
                            ).also { scale ->
                                scaleX = scale
                                scaleY = scale
                            }
                        }
                        .clip(RoundedCornerShape(preferencesState.value.corners.dp))
                        .shimmerEffect()
                )
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) {
            Box(modifier = Modifier.size(48.dp), contentAlignment = Alignment.Center) {
                Box(modifier = Modifier
                    .size(24.dp)
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect()
                )
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .shimmerEffect(),
            text = stringResource(id = R.string.personal_info),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            color = Color.Transparent
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect(),
                text = stringResource(id = R.string.known_for),
                style = MaterialTheme.typography.labelLarge,
                color = Color.Transparent
            )
            Text(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect(),
                text = "Depart",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Transparent
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect(),
                text = stringResource(id = R.string.gender),
                style = MaterialTheme.typography.labelLarge,
                color = Color.Transparent
            )
            Text(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect(),
                text = "Bender",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Transparent
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect(),
                text = stringResource(id = R.string.birthday),
                style = MaterialTheme.typography.labelLarge,
                color = Color.Transparent
            )
            Text(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect(),
                text = "birthday date (many years old)",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Transparent
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect(),
                text = stringResource(id = R.string.place_of_birth),
                style = MaterialTheme.typography.labelLarge,
                color = Color.Transparent
            )
            Text(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect(),
                text = "placeOfBirth on our earth",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Transparent
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .shimmerEffect(),
            text = stringResource(id = R.string.bio),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            color = Color.Transparent
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.small)
                .shimmerEffect(),
            text = "",
            style = MaterialTheme.typography.bodyLarge,
            minLines = 6
        )
    }
    Column(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(MaterialTheme.shapes.small)
                .shimmerEffect(),
            text = "Know for",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            color = Color.Transparent
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(5) {
                MediaPosterShimmer(
                    modifier = Modifier
                        .height(150.dp)
                        .aspectRatio(0.675f)
                        .clip(RoundedCornerShape(preferencesState.value.corners.dp))
                        .shimmerEffect(),
                    showTitle = preferencesState.value.showTitle
                )
            }
        }
    }
}
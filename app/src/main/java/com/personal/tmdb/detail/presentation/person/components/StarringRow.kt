package com.personal.tmdb.detail.presentation.person.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.core.presentation.components.MediaPoster
import com.personal.tmdb.detail.domain.models.CombinedCreditsInfo

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StarringRow(
    modifier: Modifier = Modifier,
    onNavigateTo: (route: String) -> Unit,
    combinedCredits: () -> CombinedCreditsInfo
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = if ((combinedCredits().castMediaInfo?.size ?: 0) > 2) stringResource(id = R.string.person_starring) else stringResource(id = R.string.known_for),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium
        )
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if ((combinedCredits().castMediaInfo?.size ?: 0) > 2) {
                    combinedCredits().castMediaInfo?.let { castMediaInfo ->
                        items(
                            count = castMediaInfo.size,
                            key = { castMediaInfo[it].id }
                        ) { index ->
                            val mediaInfo = castMediaInfo[index]
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
                } else {
                    combinedCredits().crewMediaInfo?.let { crewMediaInfo ->
                        items(
                            count = crewMediaInfo.size,
                            key = { crewMediaInfo[it].id }
                        ) { index ->
                            val mediaInfo = crewMediaInfo[index]
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
    }
}
package com.personal.tmdb.detail.presentation.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.UserState
import com.personal.tmdb.core.presentation.components.ProvideContentColorTextStyle
import com.personal.tmdb.core.util.formatDate
import com.personal.tmdb.core.util.formatRuntime
import com.personal.tmdb.core.util.formatTvShowRuntime
import com.personal.tmdb.detail.domain.models.MediaDetailInfo

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailTitle(
    modifier: Modifier = Modifier,
    info: () -> MediaDetailInfo,
    userState: () -> UserState
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        info().name?.let { title ->
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium
            )
        }
        metadata(info = info, userState = userState).takeIf { it.isNotEmpty() }?.let { components ->
            ProvideContentColorTextStyle(
                contentColor = MaterialTheme.colorScheme.surfaceVariant,
                textStyle = MaterialTheme.typography.bodyLarge
            ) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    components.forEachIndexed { index, component ->
                        component()
                        if (index != components.lastIndex) {
                            Icon(
                                modifier = Modifier
                                    .size(6.dp)
                                    .align(Alignment.CenterVertically),
                                painter = painterResource(id = R.drawable.icon_fiber_manual_record_fill1_wght400),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun metadata(
    info: () -> MediaDetailInfo,
    userState: () -> UserState
): List<@Composable (FlowRowScope.() -> Unit)> {
    return with(info()) {
        val userCountryCode = userState().userInfo?.iso31661 ?: "US"
        val tvShowContentRating = contentRatings?.contentRatingsResults?.find { it.iso31661 == userCountryCode }?.rating?.takeIf { it.isNotEmpty() }
        val movieContentRating = releaseDates?.releaseDatesResults?.find { it.iso31661 == userCountryCode }?.releaseDates
            ?.find { it.certification.isNotEmpty() }?.certification?.takeIf { it.isNotEmpty() }
        buildList<@Composable FlowRowScope.() -> Unit> {
            releaseDate?.let { releaseDate ->
                add {
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = formatDate(releaseDate)
                    )
                }
            }
            tvShowContentRating?.let { showRating ->
                add {
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = showRating
                    )
                }
            }
            movieContentRating?.let { movieRating ->
                add {
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = movieRating
                    )
                }
            }
            runtime?.let { runtime ->
                val context = LocalContext.current
                add {
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = formatRuntime(runtime, context)
                    )
                }
            }
            if (numberOfSeasons != null && numberOfEpisodes != null) {
                add {
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = formatTvShowRuntime(numberOfSeasons, numberOfEpisodes)
                    )
                }
            }
        }
    }
}
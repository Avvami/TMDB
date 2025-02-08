package com.personal.tmdb.detail.presentation.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.core.navigation.Route
import com.personal.tmdb.core.presentation.components.HtmlTextContainer
import com.personal.tmdb.core.domain.util.MediaType
import com.personal.tmdb.core.domain.util.formatDate
import com.personal.tmdb.detail.domain.models.ReviewInfo
import com.personal.tmdb.detail.presentation.detail.DetailUiEvent

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailReview(
    modifier: Modifier = Modifier,
    reviews: () -> List<ReviewInfo>,
    mediaId: () -> Int,
    mediaType: () -> MediaType,
    detailUiEvent: (DetailUiEvent) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (reviews().size > 1) {
                        Modifier.clickable(
                            interactionSource = null,
                            indication = null
                        ) {
                            detailUiEvent(
                                DetailUiEvent.OnNavigateTo(
                                    Route.Reviews(
                                        mediaType = mediaType().name.lowercase(),
                                        mediaId = mediaId()
                                    )
                                )
                            )
                        }
                    } else {
                        Modifier
                    }
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.latest_review),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium
            )
            if (reviews().size > 1) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.all_reviews),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .clickable {
                    detailUiEvent(
                        DetailUiEvent.OnNavigateTo(
                            Route.Reviews(
                                mediaType = mediaType().name.lowercase(),
                                mediaId = mediaId(),
                                selectedReviewIndex = reviews().lastIndex
                            )
                        )
                    )
                }
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            with(reviews().last()) {
                buildList<@Composable FlowRowScope.() -> Unit> {
                    authorDetails?.rating?.let { rating ->
                        add {
                            Row(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier.size(14.dp),
                                    painter = painterResource(id = R.drawable.icon_thumbs_up_down_fill1_wght400),
                                    contentDescription = "Thumbs",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = rating.toString(),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                    author?.let { author ->
                        add {
                            Text(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                text = author,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    createdAt?.let { date ->
                        add {
                            Text(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                text = formatDate(date),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.surfaceVariant
                            )
                        }
                    }
                }.takeIf { it.isNotEmpty() }?.let { components ->
                    FlowRow(
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        components.forEachIndexed { index, component ->
                            component()
                            if (index != components.lastIndex) {
                                Icon(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .align(Alignment.CenterVertically),
                                    painter = painterResource(id = R.drawable.icon_fiber_manual_record_fill1_wght400),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.surfaceVariant
                                )
                            }
                        }
                    }
                }
                content?.let { content ->
                    HtmlTextContainer(
                        text = content
                    ) {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
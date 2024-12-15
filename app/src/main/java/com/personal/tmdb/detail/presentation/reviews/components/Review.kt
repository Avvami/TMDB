package com.personal.tmdb.detail.presentation.reviews.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.formatDate
import com.personal.tmdb.detail.domain.models.ReviewInfo

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Review(
    modifier: Modifier = Modifier,
    review: ReviewInfo,
    preferencesState: State<PreferencesState>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(preferencesState.value.corners.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable { /*TODO: Navigate to reviews screen*/ }
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        with(review) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    model = C.TMDB_IMAGES_BASE_URL + C.PROFILE_W185 + authorDetails?.avatarPath,
                    placeholder = painterResource(id = R.drawable.placeholder),
                    error = painterResource(id = R.drawable.placeholder),
                    contentDescription = "Avatar",
                    contentScale = ContentScale.Crop
                )
                Column {
                    author?.let { author ->
                        Text(
                            text = author,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    buildList<@Composable FlowRowScope.() -> Unit> {
                        authorDetails?.rating?.let { rating ->
                            if (rating != 0) {
                                add {
                                    Row(
                                        modifier = Modifier
                                            .clip(MaterialTheme.shapes.extraSmall)
                                            .background(MaterialTheme.colorScheme.inverseSurface)
                                            .padding(
                                                horizontal = 4.dp,
                                                vertical = 2.dp
                                            )
                                            .align(Alignment.CenterVertically),
                                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = rating.toString(),
                                            style = MaterialTheme.typography.labelMedium,
                                            color = MaterialTheme.colorScheme.inverseOnSurface
                                        )
                                        Icon(
                                            modifier = Modifier.size(14.dp),
                                            painter = painterResource(id = R.drawable.icon_bar_chart_fill0_wght400),
                                            contentDescription = "Rating",
                                            tint = MaterialTheme.colorScheme.inverseOnSurface
                                        )
                                    }
                                }
                            }
                        }
                        createdAt?.let { date ->
                            add {
                                Text(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    text = formatDate(date),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }.takeIf { it.isNotEmpty() }?.let { components ->
                        FlowRow(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
            content?.let { content ->
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
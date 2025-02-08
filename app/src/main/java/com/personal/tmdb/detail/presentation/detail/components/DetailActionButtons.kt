package com.personal.tmdb.detail.presentation.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.UserState
import com.personal.tmdb.core.domain.util.compactDecimalFormat
import com.personal.tmdb.core.domain.util.formatVoteAverage
import com.personal.tmdb.detail.domain.models.MediaDetailInfo
import com.personal.tmdb.detail.presentation.detail.DetailUiEvent

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailActionButtons(
    modifier: Modifier = Modifier,
    info: () -> MediaDetailInfo,
    userState: () -> UserState,
    detailUiEvent: (DetailUiEvent) -> Unit
) {
    CompositionLocalProvider(
        LocalMinimumInteractiveComponentSize provides Dp.Unspecified
    ) {
        FlowRow(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (!userState().sessionId.isNullOrEmpty()) {
                Button(
                    onClick = { /*TODO*/ },
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    shape = MaterialTheme.shapes.small
                ) {
                    Icon(
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                        painter = painterResource(id = R.drawable.icon_thumbs_up_down_fill0_wght400),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(id = R.string.rate))
                }
            }
            info().voteAverage?.let { voteAverage ->
                Button(
                    onClick = { /*TODO*/ },
                    enabled = false,
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                        disabledContentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    shape = MaterialTheme.shapes.small
                ) {
                    Icon(
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                        painter = painterResource(id = R.drawable.icon_thumbs_up_down_fill1_wght400),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = MaterialTheme.typography.labelLarge.toSpanStyle()
                            ) {
                                append(formatVoteAverage(voteAverage))
                            }
                            info().voteCount?.takeIf { it != 0 }?.let { voteCount ->
                                withStyle(
                                    style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.surfaceVariant).toSpanStyle()
                                ) {
                                    append(" (${compactDecimalFormat(voteCount.toLong())})")
                                }
                            }
                        }
                    )
                }
            }
            if (!userState().sessionId.isNullOrEmpty()) {
                Button(
                    onClick = { /*TODO*/ },
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = MaterialTheme.shapes.small
                ) {
                    Icon(
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(id = R.string.list))
                }
            }
        }
    }
}

@Suppress("unused")
@Composable
fun WatchlistButton(
    modifier: Modifier = Modifier,
    addToWatchlist: () -> Unit,
    addToList: () -> Unit
) {
    CompositionLocalProvider(
        LocalMinimumInteractiveComponentSize provides Dp.Unspecified
    ) {
        Row(
            modifier = modifier
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.primary),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = addToWatchlist,
                contentPadding = PaddingValues(start = 16.dp, top = 10.dp, end = 8.dp, bottom = 10.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = MaterialTheme.shapes.small
            ) {
                Icon(
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                    painter = painterResource(id = R.drawable.icon_bookmark_fill0_wght400),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                Text(text = stringResource(id = R.string.watchlist))
            }
            VerticalDivider(
                modifier = Modifier
                    .heightIn(max = ButtonDefaults.MinHeight)
                    .padding(vertical = 10.dp),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = .1f)
            )
            FilledIconButton(
                onClick = addToList,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = Color.Transparent
                ),
                shape = MaterialTheme.shapes.small
            ) {
                Row {
                    Icon(
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }
}
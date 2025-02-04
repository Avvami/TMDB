package com.personal.tmdb.detail.presentation.episode.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.UserState
import com.personal.tmdb.core.util.formatVoteAverage
import com.personal.tmdb.detail.domain.models.EpisodeDetailsInfo

@Composable
fun EpisodeActionButtons(
    modifier: Modifier = Modifier,
    episodeDetails: () -> EpisodeDetailsInfo,
    userState: () -> UserState
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
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
        episodeDetails().voteAverage?.let { voteAverage ->
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
                        episodeDetails().voteCount?.let { voteCount ->
                            withStyle(
                                style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.surfaceVariant).toSpanStyle()
                            ) {
                                append(" ($voteCount)")
                            }
                        }
                    }
                )
            }
        }
    }
}
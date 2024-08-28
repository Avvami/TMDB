package com.personal.tmdb.detail.presentation.person.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.personal.tmdb.MainActivity
import com.personal.tmdb.R
import com.personal.tmdb.core.util.findActivity
import com.personal.tmdb.detail.data.models.ExternalIds

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExternalIdsRow(
    modifier: Modifier = Modifier,
    externalIds: ExternalIds
) {
    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            externalIds.facebookId?.let { facebookId ->
                ExternalId(
                    link = stringResource(id = R.string.facebook, facebookId),
                    iconRes = R.drawable.icon_facebook
                )
            }
            externalIds.instagramId?.let { instagramId ->
                ExternalId(
                    link = stringResource(id = R.string.instagram, instagramId),
                    iconRes = R.drawable.icon_instagram
                )
            }
            externalIds.tiktokId?.let { tiktokId ->
                ExternalId(
                    link = stringResource(id = R.string.tiktok, tiktokId),
                    iconRes = R.drawable.icon_tiktok
                )
            }
            externalIds.twitterId?.let { twitterId ->
                ExternalId(
                    link = stringResource(id = R.string.twitter_x, twitterId),
                    iconRes = R.drawable.icon_twitter_x,
                    iconSize = 18.dp
                )
            }
            externalIds.youtubeId?.let { youtubeId ->
                ExternalId(
                    link = stringResource(id = R.string.youtube, youtubeId),
                    iconRes = R.drawable.icon_youtube
                )
            }
        }
    }
}

@Composable
fun ExternalId(
    link: String,
    @DrawableRes iconRes: Int,
    iconSize: Dp? = null
) {
    val activity = LocalContext.current.findActivity() as MainActivity
    IconButton(
        onClick = { activity.openCustomChromeTab(link) }
    ) {
        Icon(
            modifier = Modifier.then(
                iconSize?.let { Modifier.size(it) } ?: Modifier
            ),
            painter = painterResource(id = iconRes),
            contentDescription = "External"
        )
    }
}
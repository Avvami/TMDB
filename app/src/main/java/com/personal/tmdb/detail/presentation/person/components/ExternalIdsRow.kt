package com.personal.tmdb.detail.presentation.person.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
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
                    link = facebookId,
                    iconRes = R.drawable.icon_facebook
                )
            }
            externalIds.imdbId?.let { imdbId ->
                ExternalId(
                    link = imdbId,
                    iconRes = R.drawable.icon_imdb
                )
            }
            externalIds.instagramId?.let { instagramId ->
                ExternalId(
                    link = instagramId,
                    iconRes = R.drawable.icon_instagram
                )
            }
            externalIds.tiktokId?.let { tiktokId ->
                ExternalId(
                    link = tiktokId,
                    iconRes = R.drawable.icon_tiktok
                )
            }
            externalIds.twitterId?.let { twitterId ->
                ExternalId(
                    link = twitterId,
                    iconRes = R.drawable.icon_twitter
                )
            }
            externalIds.wikidataId?.let { wikidataId ->
                ExternalId(
                    link = wikidataId,
                    iconRes = R.drawable.icon_wikipedia
                )
            }
            externalIds.youtubeId?.let { youtubeId ->
                ExternalId(
                    link = youtubeId,
                    iconRes = R.drawable.icon_youtube
                )
            }
        }
    }
}

@Composable
fun ExternalId(
    link: String,
    @DrawableRes iconRes: Int
) {
    IconButton(
        onClick = { /*TODO: Open link*/ }
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = "External"
        )
    }
}
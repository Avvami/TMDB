package com.personal.tmdb.detail.presentation.detail.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.core.util.C
import com.personal.tmdb.detail.data.models.Provider
import com.personal.tmdb.detail.domain.models.MediaDetailInfo
import com.personal.tmdb.detail.presentation.detail.DetailUiEvent

@Composable
fun DetailBanner(
    modifier: Modifier = Modifier,
    info: () -> MediaDetailInfo,
    watchCountry: () -> String,
    detailUiEvent: (DetailUiEvent) -> Unit
) {
    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                detailUiEvent(DetailUiEvent.ChangeAvailableDialogState)
            }
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(18 / 9f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .matchParentSize(),
                model = C.TMDB_IMAGES_BASE_URL + C.BACKDROP_W1280 + info().backdropPath,
                contentDescription = "Backdrop",
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.placeholder),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, MaterialTheme.colorScheme.surfaceContainer)
                        )
                    )
            )
        }
        info().watchProviders?.takeIf { it.isNotEmpty() }?.let { watchProviders ->
            watchProviders.getOrElse(watchCountry()) {
                watchProviders.entries.firstOrNull()?.key?.let { country ->
                    detailUiEvent(DetailUiEvent.SetSelectedCountry(country))
                }
                watchProviders.entries.firstOrNull()?.value
            }?.let { available ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    when {
                        available.free?.firstOrNull() != null -> {
                            WatchNow(
                                source = available.free.first(),
                                available = R.string.for_free
                            )
                        }
                        available.flatrate?.firstOrNull() != null -> {
                            WatchNow(
                                source = available.flatrate.first(),
                                available = R.string.now_streaming
                            )
                        }
                        available.rent?.firstOrNull() != null -> {
                            WatchNow(
                                source = available.rent.first(),
                                available = R.string.rent_buy_available
                            )
                        }
                        available.buy?.firstOrNull() != null -> {
                            WatchNow(
                                source = available.buy.first(),
                                available = R.string.rent_buy_available
                            )
                        }
                        available.ads?.firstOrNull() != null -> {
                            WatchNow(
                                source = available.ads.first(),
                                available = R.string.with_ads
                            )
                        }
                    }
                }
            }
        }
//        if (availableState().isDialogShown) {
//            AvailableDialog(
//                watchProviders = { watchProviders },
//                availableSearchQuery = availableSearchQuery,
//                availableCountries = availableCountries,
//                availableState = availableState,
//                detailUiEvent = detailUiEvent
//            )
//        }
    }
}

@Composable
fun WatchNow(
    source: Provider,
    @StringRes available: Int
) {
    AsyncImage(
        modifier = Modifier
            .size(34.dp)
            .clip(MaterialTheme.shapes.small),
        model = C.TMDB_IMAGES_BASE_URL + C.LOGO_W92 + source.logoPath,
        placeholder = painterResource(id = R.drawable.placeholder),
        error = painterResource(id = R.drawable.placeholder),
        contentDescription = source.providerName ?: "Watch provider logo",
        contentScale = ContentScale.Crop
    )
    Column {
        Text(
            text = stringResource(id = available),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.surfaceVariant
        )
        Text(
            text = stringResource(id = R.string.watch_now),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
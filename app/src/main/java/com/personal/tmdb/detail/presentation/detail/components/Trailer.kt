package com.personal.tmdb.detail.presentation.detail.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.core.util.C
import com.personal.tmdb.detail.data.models.Provider
import com.personal.tmdb.detail.domain.models.MediaDetailInfo
import com.personal.tmdb.detail.presentation.detail.AvailableState
import com.personal.tmdb.detail.presentation.detail.DetailUiEvent
import com.personal.tmdb.ui.theme.backgroundLight
import com.personal.tmdb.ui.theme.onBackgroundLight
import com.personal.tmdb.ui.theme.tmdbDarkBlue

@Composable
fun Trailer(
    info: () -> MediaDetailInfo,
    availableSearchQuery: State<String>,
    availableCountries: State<Collection<String>?>,
    availableState: () -> AvailableState,
    detailUiEvent: (DetailUiEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = R.string.tmdb),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.surfaceContainerLow,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center
        )
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = C.TMDB_IMAGES_BASE_URL + C.BACKDROP_W1280 + info().backdropPath,
            contentDescription = "Backdrop",
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.placeholder),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(onBackgroundLight.copy(.3f))
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            if (false) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = "Play",
                    tint = backgroundLight
                )
            }
        }
        if (!info().watchProviders.isNullOrEmpty()) {
            info().watchProviders?.let { watchProviders ->
                watchProviders.getOrElse(availableState().selectedCountry) {
                    watchProviders.entries.firstOrNull()?.key?.let { country ->
                        detailUiEvent(DetailUiEvent.SetSelectedCountry(country))
                    }
                    watchProviders.entries.firstOrNull()?.value
                }?.let { available ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { detailUiEvent(DetailUiEvent.ChangeAvailableDialogState) }
                            .background(tmdbDarkBlue.copy(alpha = .8f))
                            .padding(4.dp)
                            .align(Alignment.BottomCenter),
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
                if (availableState().isDialogShown) {
                    AvailableDialog(
                        watchProviders = { watchProviders },
                        availableSearchQuery = availableSearchQuery,
                        availableCountries = availableCountries,
                        availableState = availableState,
                        detailUiEvent = detailUiEvent
                    )
                }
            }
        }
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
        contentDescription = source.providerName ?: "Logo",
        contentScale = ContentScale.Crop
    )
    Column {
        Text(
            text = stringResource(id = available),
            style = MaterialTheme.typography.labelLarge,
            color = backgroundLight.copy(alpha = .7f)
        )
        Text(
            text = stringResource(id = R.string.watch_now),
            style = MaterialTheme.typography.labelLarge,
            color = backgroundLight
        )
    }
}
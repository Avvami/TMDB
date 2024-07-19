package com.personal.tmdb.detail.presentation.detail.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.personal.tmdb.R
import com.personal.tmdb.detail.data.models.Season
import com.personal.tmdb.detail.domain.models.MediaDetailInfo
import com.personal.tmdb.detail.presentation.detail.DetailUiEvent
import com.personal.tmdb.detail.presentation.detail.SeasonState
import com.personal.tmdb.ui.theme.backgroundLight
import com.personal.tmdb.ui.theme.onBackgroundLight
import com.personal.tmdb.ui.theme.outlineVariantLight

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Episodes(
    onNavigateTo: (route: String) -> Unit,
    seasonState: () -> SeasonState,
    seasons: () -> List<Season>,
    detailInfo: () -> MediaDetailInfo,
    selectedSeasonNumber: () -> Int,
    isSeasonDropdownExpanded: () -> Boolean,
    isSeasonOverviewCollapsed: () -> Boolean,
    detailUiEvent: (DetailUiEvent) -> Unit
) {
    Column(
        modifier = Modifier.padding(PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp))
    ) {
        if (seasons().size != 1) {
            seasons().find { it.seasonNumber == selectedSeasonNumber() }?.let { seasonInfo ->
                Button(
                    onClick = { detailUiEvent(DetailUiEvent.ChangeSeasonDropdownState) },
                    shape = MaterialTheme.shapes.small,
                    contentPadding = PaddingValues(start = 16.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
                ) {
                    Text(
                        text = seasonInfo.name ?: seasonInfo.seasonNumber.toString(),
                        style = MaterialTheme.typography.titleMedium
                    )
                    val rotation by animateIntAsState(
                        targetValue = if (isSeasonDropdownExpanded()) 180 else 0,
                        label = "Rotation animation"
                    )
                    Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                    Icon(
                        modifier = Modifier.rotate(rotation.toFloat()),
                        imageVector = Icons.Rounded.ArrowDropDown,
                        contentDescription = "Dropdown"
                    )
                    if (isSeasonDropdownExpanded()) {
                        Dialog(
                            onDismissRequest = { detailUiEvent(DetailUiEvent.ChangeSeasonDropdownState) },
                            properties = DialogProperties(dismissOnClickOutside = false)
                        ) {
                            (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(.85f)
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CompositionLocalProvider(
                                        LocalOverscrollConfiguration provides null
                                    ) {
                                        LazyColumn {
                                            items(
                                                count = seasons().size,
                                                key = { seasons()[it].id }
                                            ) { index ->
                                                val season = seasons()[index]
                                                Text(
                                                    modifier = Modifier
                                                        .fillMaxWidth(.7f)
                                                        .clickable {
                                                            detailUiEvent(
                                                                DetailUiEvent.SetSelectedSeason(detailInfo().id, season.seasonNumber)
                                                            )
                                                            detailUiEvent(DetailUiEvent.ChangeSeasonDropdownState)
                                                        }
                                                        .padding(
                                                            horizontal = 16.dp,
                                                            vertical = 12.dp
                                                        ),
                                                    text = season.name ?: season.seasonNumber.toString(),
                                                    fontSize = if (season.seasonNumber == selectedSeasonNumber()) 22.sp else 18.sp,
                                                    fontWeight =  if (season.seasonNumber == selectedSeasonNumber()) FontWeight.SemiBold else FontWeight.Normal,
                                                    color = if (season.seasonNumber == selectedSeasonNumber()) backgroundLight else outlineVariantLight,
                                                    textAlign = TextAlign.Center
                                                )
                                            }
                                        }
                                    }
                                }
                                IconButton(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .size(56.dp),
                                    onClick = { detailUiEvent(DetailUiEvent.ChangeSeasonDropdownState) },
                                    colors = IconButtonDefaults.iconButtonColors(containerColor = backgroundLight, contentColor = onBackgroundLight)
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Close,
                                        contentDescription = "Close"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        if (seasonState().isLoading) {
            EpisodesShimmer()
        } else {
            seasonState().seasonInfo?.let { seasonInfo ->
                seasonInfo.overview?.let { overview ->
                    Column(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.overview),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(MaterialTheme.shapes.extraSmall)
                                .animateContentSize()
                                .clickable {
                                    detailUiEvent(DetailUiEvent.ChangeCollapsedSeasonOverview)
                                },
                            text = overview,
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = if (isSeasonOverviewCollapsed()) 3 else Int.MAX_VALUE,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                if (seasonInfo.episodes.isNullOrEmpty()) {
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
                        text = stringResource(id = R.string.no_episodes),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                } else {
                    Column {
                        seasonInfo.episodes.fastForEachIndexed { index, episodeInfo ->
                            Episode(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clip(MaterialTheme.shapes.extraSmall)
                                    .clickable { /*TODO: Navigate to episode detail*/ },
                                episodeInfo = { episodeInfo }
                            )
                            if (index != seasonInfo.episodes.lastIndex) {
                                HorizontalDivider()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EpisodesShimmer() {
    Column {
        repeat(8) { index ->
            EpisodeShimmer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            if (index != 7) {
                HorizontalDivider()
            }
        }
    }
}
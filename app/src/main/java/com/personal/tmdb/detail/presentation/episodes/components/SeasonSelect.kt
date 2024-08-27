package com.personal.tmdb.detail.presentation.episodes.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.personal.tmdb.core.util.shimmerEffect
import com.personal.tmdb.detail.presentation.episodes.SeasonState
import com.personal.tmdb.detail.presentation.episodes.EpisodesUiEvent
import com.personal.tmdb.ui.theme.backgroundLight
import com.personal.tmdb.ui.theme.onBackgroundLight
import com.personal.tmdb.ui.theme.outlineVariantLight

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.seasonSelect(
    seasonState: () -> SeasonState,
    selectedSeasonNumber: () -> Int,
    isSeasonDialogOpen: () -> Boolean,
    episodesUiEvent: (EpisodesUiEvent) -> Unit
) {
    if (seasonState().mediaDetail == null && seasonState().error == null) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .shimmerEffect(),
                    text = "Season name",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Transparent
                )
            }
        }
    } else {
        seasonState().mediaDetail?.let { mediaDetail ->
            mediaDetail.seasons?.let { seasons ->
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceContainer)
                            .then(
                                if (seasons.size > 1) {
                                    Modifier.clickable {
                                        episodesUiEvent(EpisodesUiEvent.ChangeSeasonDialogState)
                                    }
                                } else {
                                    Modifier
                                }
                            )
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val season = seasons.find { it.seasonNumber == selectedSeasonNumber() }
                        Text(
                            modifier = Modifier.weight(1f),
                            text = season?.name ?: season?.seasonNumber?.toString() ?: "",
                            style = MaterialTheme.typography.titleMedium
                        )
                        if (seasons.size > 1) {
                            val rotation by animateIntAsState(
                                targetValue = if (isSeasonDialogOpen()) 180 else 0,
                                label = "Rotation animation"
                            )
                            Icon(
                                modifier = Modifier.rotate(rotation.toFloat()),
                                imageVector = Icons.Rounded.ArrowDropDown,
                                contentDescription = "Dropdown"
                            )
                        }
                        if (isSeasonDialogOpen()) {
                            Dialog(
                                onDismissRequest = { episodesUiEvent(EpisodesUiEvent.ChangeSeasonDialogState) },
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
                                                items(seasons) { season ->
                                                    Text(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .clickable {
                                                                episodesUiEvent(
                                                                    EpisodesUiEvent.SetSelectedSeason(
                                                                        mediaDetail.id,
                                                                        season.seasonNumber
                                                                    )
                                                                )
                                                                episodesUiEvent(EpisodesUiEvent.ChangeSeasonDialogState)
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
                                        onClick = { episodesUiEvent(EpisodesUiEvent.ChangeSeasonDialogState) },
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
        }
    }
}
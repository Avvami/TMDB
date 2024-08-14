package com.personal.tmdb.detail.presentation.collection.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import com.personal.tmdb.R
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.MediaListViewShimmer
import com.personal.tmdb.core.util.shimmerEffect

@Composable
fun CollectionScreenShimmer(
    innerPadding: PaddingValues,
    preferencesState: State<PreferencesState>
) {
    MediaListViewShimmer(
        modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
        preferencesState = preferencesState,
        topItemContent = {
            val sidePadding = (-16).dp
            Column(
                modifier = Modifier
                    .layout { measurable, constraints ->
                        val placeable =
                            measurable.measure(constraints.offset(horizontal = -sidePadding.roundToPx() * 2))
                        layout(
                            width = placeable.width + sidePadding.roundToPx() * 2,
                            height = placeable.height
                        ) {
                            placeable.place(+sidePadding.roundToPx(), 0)
                        }
                    }
                    .padding(bottom = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerLow)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .padding(top = innerPadding.calculateTopPadding()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.small)
                                .padding(horizontal = 16.dp)
                                .shimmerEffect(),
                            text = "",
                            style = MaterialTheme.typography.bodyLarge,
                            minLines = 3
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 4.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    modifier = Modifier
                                        .clip(MaterialTheme.shapes.small)
                                        .shimmerEffect(),
                                    text = "00",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Transparent
                                )
                                Text(
                                    modifier = Modifier
                                        .clip(MaterialTheme.shapes.small)
                                        .shimmerEffect(),
                                    text = stringResource(id = R.string.number_of_movies),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.Transparent
                                )
                            }
                            Column(
                                modifier = Modifier.padding(horizontal = 4.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    modifier = Modifier
                                        .clip(MaterialTheme.shapes.small)
                                        .shimmerEffect(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "0.0",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Transparent
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_bar_chart_fill0_wght400),
                                        contentDescription = "Rating",
                                        tint = Color.Transparent
                                    )
                                }
                                Text(
                                    modifier = Modifier
                                        .clip(MaterialTheme.shapes.small)
                                        .shimmerEffect(),
                                    text = stringResource(id = R.string.average_rating),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.Transparent
                                )
                            }
                        }
                    }
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = stringResource(id = R.string.tmdb),
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center
                    )
                }
                SuggestionChip(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clip(SuggestionChipDefaults.shape)
                        .shimmerEffect()
                        .height(SuggestionChipDefaults.Height),
                    enabled = false,
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = Color.Transparent,
                        labelColor = Color.Transparent,
                        iconContentColor = Color.Transparent,
                        disabledLabelColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        disabledIconContentColor = Color.Transparent
                    ),
                    border = BorderStroke(width = 1.dp, color = Color.Transparent),
                    onClick = {},
                    label = {
                        Text(text = stringResource(id = R.string.sort_by))
                    },
                    icon = {
                        Icon(
                            modifier = Modifier.size(SuggestionChipDefaults.IconSize),
                            imageVector = Icons.Rounded.ArrowDropDown,
                            contentDescription = "Dropdown"
                        )
                    }
                )
            }
        }
    )
}
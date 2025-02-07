package com.personal.tmdb.detail.presentation.collection.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.detail.domain.util.CollectionSortType
import com.personal.tmdb.detail.presentation.collection.CollectionState
import com.personal.tmdb.detail.presentation.collection.CollectionUiEvent

@Composable
fun CollectionSortingChips(
    modifier: Modifier = Modifier,
    collectionState: () -> CollectionState,
    collectionUiEvent: (CollectionUiEvent) -> Unit
) {
    CompositionLocalProvider(
        LocalMinimumInteractiveComponentSize provides Dp.Unspecified
    ) {
        var showBottomSheet by rememberSaveable { mutableStateOf(false) }
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SuggestionChip(
                onClick = { showBottomSheet = true },
                icon = {
                    Icon(
                        modifier = Modifier.size(SuggestionChipDefaults.IconSize),
                        painter = painterResource(id = R.drawable.icon_page_info_fill0_wght400),
                        contentDescription = "Sort by"
                    )
                },
                label = {
                    Text(text = stringResource(id = R.string.sort_by))
                },
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    iconContentColor = MaterialTheme.colorScheme.surfaceVariant,
                    labelColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                border = null
            )
            AnimatedVisibility(
                visible = collectionState().sortType != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                FilterChip(
                    selected = true,
                    onClick = {
                        collectionUiEvent(CollectionUiEvent.SetSortType(null))
                    },
                    label = {
                        Text(text = collectionState().displayingSortType.asString())
                    },
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.size(FilterChipDefaults.IconSize),
                            imageVector = Icons.Rounded.Clear,
                            contentDescription = "Clear"
                        )
                    },
                    trailingIcon = {
                        val rotation by animateIntAsState(
                            targetValue = when (collectionState().sortType) {
                                CollectionSortType.RATING_ASC -> 90
                                CollectionSortType.RATING_DESC -> -90
                                CollectionSortType.RELEASE_DATE_ASC -> 90
                                CollectionSortType.RELEASE_DATE_DESC -> -90
                                null -> -90
                            },
                            label = "Rotation animation"
                        )
                        Icon(
                            modifier = Modifier
                                .rotate(rotation.toFloat())
                                .size(FilterChipDefaults.IconSize),
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Arrow"
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
                        selectedTrailingIconColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }
        if (showBottomSheet) {
            CollectionSortingBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sortType = collectionState()::sortType,
                collectionUiEvent = collectionUiEvent
            )
        }
    }
}
package com.personal.tmdb.detail.presentation.collection.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.core.presentation.components.CustomDragHandle
import com.personal.tmdb.core.presentation.components.CustomListItem
import com.personal.tmdb.detail.domain.util.CollectionSortType
import com.personal.tmdb.detail.presentation.collection.CollectionUiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionSortingBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    sortType: () -> CollectionSortType?,
    collectionUiEvent: (CollectionUiEvent) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        dragHandle = { CustomDragHandle() },
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Column(
            modifier = modifier.padding(bottom = 8.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(id = R.string.sort_by),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                androidx.compose.animation.AnimatedVisibility(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    visible = sortType() != null,
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    Text(
                        modifier = Modifier.clickable(
                            interactionSource = null,
                            indication = null
                        ) {
                            collectionUiEvent(CollectionUiEvent.SetSortType(null))
                        },
                        text = stringResource(id = R.string.clear),
                        style = MaterialTheme.typography.titleSmall,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            CustomListItem(
                onClick = {
                    collectionUiEvent(
                        CollectionUiEvent.SetSortType(
                            when (sortType()) {
                                CollectionSortType.RELEASE_DATE_ASC -> CollectionSortType.RELEASE_DATE_DESC
                                CollectionSortType.RELEASE_DATE_DESC -> CollectionSortType.RELEASE_DATE_ASC
                                else -> CollectionSortType.RELEASE_DATE_DESC
                            }
                        )
                    )
                },
                selected = sortType() == CollectionSortType.RELEASE_DATE_ASC || sortType() == CollectionSortType.RELEASE_DATE_DESC,
                leadingContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_calendar_month_fill0_wght400),
                        contentDescription = null
                    )
                },
                headlineContent = {
                    Text(text = stringResource(id = R.string.release_date))
                },
                trailingContent = {
                    val rotation by animateIntAsState(
                        targetValue = when (sortType()) {
                            CollectionSortType.RELEASE_DATE_ASC -> 90
                            CollectionSortType.RELEASE_DATE_DESC -> -90
                            else -> -90
                        },
                        label = "Rotation animation"
                    )
                    AnimatedVisibility(
                        visible = sortType() == CollectionSortType.RELEASE_DATE_ASC || sortType() == CollectionSortType.RELEASE_DATE_DESC,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Icon(
                            modifier = Modifier.rotate(rotation.toFloat()),
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Arrow"
                        )
                    }
                }
            )
            CustomListItem(
                onClick = {
                    collectionUiEvent(
                        CollectionUiEvent.SetSortType(
                            when (sortType()) {
                                CollectionSortType.RATING_ASC -> CollectionSortType.RATING_DESC
                                CollectionSortType.RATING_DESC -> CollectionSortType.RATING_ASC
                                else -> CollectionSortType.RATING_DESC
                            }
                        )
                    )
                },
                selected = sortType() == CollectionSortType.RATING_ASC || sortType() == CollectionSortType.RATING_DESC,
                leadingContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_thumbs_up_down_fill0_wght400),
                        contentDescription = null
                    )
                },
                headlineContent = {
                    Text(text = stringResource(id = R.string.rating))
                },
                trailingContent = {
                    val rotation by animateIntAsState(
                        targetValue = when (sortType()) {
                            CollectionSortType.RATING_ASC -> 90
                            CollectionSortType.RATING_DESC -> -90
                            else -> -90
                        },
                        label = "Rotation animation"
                    )
                    AnimatedVisibility(
                        visible = sortType() == CollectionSortType.RATING_ASC || sortType() == CollectionSortType.RATING_DESC,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Icon(
                            modifier = Modifier.rotate(rotation.toFloat()),
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Arrow"
                        )
                    }
                }
            )
        }
    }
}
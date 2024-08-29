package com.personal.tmdb.detail.presentation.person.components

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.detail.presentation.person.PersonCreditsState
import com.personal.tmdb.detail.presentation.person.PersonUiEvent

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SortModalBottomSheet(
    personCreditsState: () -> PersonCreditsState,
    personUiEvent: (PersonUiEvent) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = {
            personUiEvent(PersonUiEvent.ChangeBottomSheetState)
        }
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(id = R.string.sort_by),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                androidx.compose.animation.AnimatedVisibility(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    visible = personCreditsState().selectedDepartment != "" || personCreditsState().selectedMediaType != null,
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    Text(
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            personUiEvent(PersonUiEvent.SortPersonCredits(personCreditsState().selectedDepartment, personCreditsState().selectedMediaType))
                        },
                        text = stringResource(id = R.string.clear),
                        style = MaterialTheme.typography.titleSmall,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.media_type),
                    style = MaterialTheme.typography.titleMedium
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FilterChip(
                        modifier = Modifier.height(FilterChipDefaults.Height),
                        selected = personCreditsState().selectedMediaType == MediaType.MOVIE,
                        onClick = {
                            personUiEvent(PersonUiEvent.SortPersonCredits(mediaType = MediaType.MOVIE))
                        },
                        label = { Text(text = stringResource(id = R.string.movies)) }
                    )
                    FilterChip(
                        modifier = Modifier.height(FilterChipDefaults.Height),
                        selected = personCreditsState().selectedMediaType == MediaType.TV,
                        onClick = { personUiEvent(PersonUiEvent.SortPersonCredits(mediaType = MediaType.TV)) },
                        label = { Text(text = stringResource(id = R.string.tv_shows)) }
                    )
                }
            }
            personCreditsState().personCredits?.credits?.keys?.let { departments ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.department),
                        style = MaterialTheme.typography.titleMedium
                    )
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        departments.forEach { department ->
                            FilterChip(
                                modifier = Modifier.height(FilterChipDefaults.Height),
                                selected = personCreditsState().selectedDepartment == department,
                                onClick = { personUiEvent(PersonUiEvent.SortPersonCredits(department = department)) },
                                label = { Text(text = department ?: stringResource(id = R.string.acting)) }
                            )
                        }
                    }
                }
            }
        }
    }
}
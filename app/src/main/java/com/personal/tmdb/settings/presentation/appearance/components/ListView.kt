package com.personal.tmdb.settings.presentation.appearance.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.UiEvent
import com.personal.tmdb.core.presentation.PreferencesState

@Composable
fun ListView(
    preferencesState: State<PreferencesState>,
    uiEvent: (UiEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val cardsSelectedColor by animateColorAsState(
            targetValue = if (preferencesState.value.useCards) MaterialTheme.colorScheme.surfaceContainerHigh else
                MaterialTheme.colorScheme.surfaceContainer,
            label = "Color anim"
        )
        val postersSelectedColor by animateColorAsState(
            targetValue = if (!preferencesState.value.useCards) MaterialTheme.colorScheme.surfaceContainerHigh else
                MaterialTheme.colorScheme.surfaceContainer,
            label = "Color anim"
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(id = R.string.list_view),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = null,
                        indication = null
                    ) {
                        uiEvent(UiEvent.SetUseCards(true))
                    },
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .background(cardsSelectedColor)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .height(64.dp)
                                .aspectRatio(0.675f)
                                .clip(MaterialTheme.shapes.small)
                                .background(MaterialTheme.colorScheme.outlineVariant)
                        )
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(10.dp)
                                        .clip(MaterialTheme.shapes.extraSmall)
                                        .background(MaterialTheme.colorScheme.outlineVariant)
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(8.dp)
                                        .clip(MaterialTheme.shapes.extraSmall)
                                        .background(MaterialTheme.colorScheme.outlineVariant.copy(.4f))
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(24.dp)
                                    .clip(MaterialTheme.shapes.extraSmall)
                                    .background(MaterialTheme.colorScheme.outlineVariant.copy(.4f))
                            )
                        }
                    }
                    AnimatedContent(
                        targetState = preferencesState.value.useCards,
                        label = "Radio btn anim",
                        transitionSpec = {
                            scaleIn(initialScale = .6f) + fadeIn() togetherWith scaleOut(targetScale = .6f) + fadeOut()
                        }
                    ) { targetState ->
                        if (targetState) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_radio_button_checked_fill0_wght400),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_radio_button_unchecked_fill0_wght400),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                Text(
                    text = stringResource(id = R.string.cards),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = null,
                        indication = null
                    ) {
                        uiEvent(UiEvent.SetUseCards(false))
                    },
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .background(postersSelectedColor)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .height(64.dp)
                                .aspectRatio(0.675f)
                                .clip(MaterialTheme.shapes.small)
                                .background(MaterialTheme.colorScheme.outlineVariant)
                        )
                        Box(
                            modifier = Modifier
                                .height(64.dp)
                                .aspectRatio(0.675f)
                                .clip(MaterialTheme.shapes.small)
                                .background(MaterialTheme.colorScheme.outlineVariant)
                        )
                    }
                    AnimatedContent(
                        targetState = preferencesState.value.useCards,
                        label = "Radio btn anim",
                        transitionSpec = {
                            scaleIn(initialScale = .6f) + fadeIn() togetherWith scaleOut(targetScale = .6f) + fadeOut()
                        }
                    ) { targetState ->
                        if (targetState) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_radio_button_unchecked_fill0_wght400),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_radio_button_checked_fill0_wght400),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                Text(
                    text = stringResource(id = R.string.posters),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
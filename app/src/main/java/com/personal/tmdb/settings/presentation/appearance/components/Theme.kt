package com.personal.tmdb.settings.presentation.appearance.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.UiEvent
import com.personal.tmdb.core.presentation.PreferencesState

@Composable
fun Theme(
    preferencesState: State<PreferencesState>,
    uiEvent: (UiEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stringResource(id = R.string.theme),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            SegmentedButton(
                selected = preferencesState.value.darkTheme == false,
                onClick = { uiEvent(UiEvent.SetTheme(false)) },
                shape = RoundedCornerShape(topStart = 28.dp, bottomStart = 28.dp),
                label = { Text(text = stringResource(id = R.string.light)) },
                icon = {
                    AnimatedContent(
                        targetState = preferencesState.value.darkTheme == false,
                        label = "Icon change anim",
                        transitionSpec = {
                            scaleIn(initialScale = .6f) + fadeIn() togetherWith scaleOut(targetScale = .6f) + fadeOut()
                        }
                    ) { targetState ->
                        if (targetState) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_light_mode_fill1_wght400),
                                contentDescription = stringResource(id = R.string.light)
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_light_mode_fill0_wght400),
                                contentDescription = stringResource(id = R.string.light)
                            )
                        }
                    }
                },
                colors = SegmentedButtonDefaults.colors(
                    inactiveContainerColor = Color.Transparent
                )
            )
            SegmentedButton(
                selected = preferencesState.value.darkTheme == null,
                onClick = { uiEvent(UiEvent.SetTheme(null)) },
                shape = RectangleShape,
                label = { Text(text = stringResource(id = R.string.system)) },
                icon = {
                    AnimatedContent(
                        targetState = preferencesState.value.darkTheme == null,
                        label = "Icon change anim",
                        transitionSpec = {
                            scaleIn(initialScale = .6f) + fadeIn() togetherWith scaleOut(targetScale = .6f) + fadeOut()
                        }
                    ) { targetState ->
                        if (targetState) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_settings_night_sight_fill1_wght400),
                                contentDescription = stringResource(id = R.string.system)
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_settings_night_sight_fill0_wght400),
                                contentDescription = stringResource(id = R.string.system)
                            )
                        }
                    }
                },
                colors = SegmentedButtonDefaults.colors(
                    inactiveContainerColor = Color.Transparent
                )
            )
            SegmentedButton(
                selected = preferencesState.value.darkTheme == true,
                onClick = { uiEvent(UiEvent.SetTheme(true)) },
                shape = RoundedCornerShape(topEnd = 28.dp, bottomEnd = 28.dp),
                label = { Text(text = stringResource(id = R.string.dark)) },
                icon = {
                    AnimatedContent(
                        targetState = preferencesState.value.darkTheme == true,
                        label = "Icon change anim",
                        transitionSpec = {
                            scaleIn(initialScale = .6f) + fadeIn() togetherWith scaleOut(targetScale = .6f) + fadeOut()
                        }
                    ) { targetState ->
                        if (targetState) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_dark_mode_fill1_wght400),
                                contentDescription = stringResource(id = R.string.dark)
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_dark_mode_fill0_wght400),
                                contentDescription = stringResource(id = R.string.dark)
                            )
                        }
                    }
                },
                colors = SegmentedButtonDefaults.colors(
                    inactiveContainerColor = Color.Transparent
                )
            )
        }
    }
}
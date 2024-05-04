package com.personal.tmdb.home.presentation.home.components.modal

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.personal.tmdb.R
import com.personal.tmdb.UiEvent
import com.personal.tmdb.core.presentation.preferences.PreferencesState

@Composable
fun HomeModalDrawer(
    drawerState: DrawerState,
    closeDrawer: () -> Unit,
    navigateToAuthScreen: () -> Unit,
    preferencesState: State<PreferencesState>,
    uiEvent: (UiEvent) -> Unit
) {
    if (drawerState.targetValue == DrawerValue.Open) {
        BackHandler {
            closeDrawer()
        }
    }
    ModalDrawerSheet(
        drawerContainerColor = MaterialTheme.colorScheme.background,
        drawerContentColor = MaterialTheme.colorScheme.onBackground,
        windowInsets = WindowInsets(top = 0.dp)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            closeDrawer()
                            navigateToAuthScreen()
                        }
                        .statusBarsPadding()
                        .padding(start = 16.dp, top = 4.dp, end = 4.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.padding(top = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceContainerHighest),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                modifier = Modifier.size(28.dp),
                                painter = painterResource(id = R.drawable.icon_login_fill0_wght400),
                                contentDescription = stringResource(id = R.string.login)
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.login),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    AnimatedContent(
                        targetState = preferencesState.value.isDark,
                        label = "Swap icon button anim",
                        transitionSpec = {
                            slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start, initialOffset = { it }) + scaleIn() togetherWith
                                    slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start) + scaleOut()
                        }
                    ) { targetState ->
                        if (targetState) {
                            IconButton(onClick = { uiEvent(UiEvent.SetDarkMode(false)) }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_light_mode_fill1_wght400),
                                    contentDescription = stringResource(id = R.string.light_mode)
                                )
                            }
                        } else {
                            IconButton(onClick = { uiEvent(UiEvent.SetDarkMode(true)) }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_dark_mode_fill1_wght400),
                                    contentDescription = stringResource(id = R.string.dark_mode)
                                )
                            }
                        }
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { /*TODO*/ }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_settings_fill0_wght400),
                        contentDescription = stringResource(id = R.string.settings)
                    )
                    Text(
                        text = stringResource(id = R.string.settings),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}
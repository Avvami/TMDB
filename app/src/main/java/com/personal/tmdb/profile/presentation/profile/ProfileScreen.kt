package com.personal.tmdb.profile.presentation.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.personal.tmdb.R
import com.personal.tmdb.UiEvent
import com.personal.tmdb.UserState
import com.personal.tmdb.core.navigation.Route
import com.personal.tmdb.core.presentation.components.CustomListItem
import com.personal.tmdb.profile.presentation.profile.components.ProfileBox

@Composable
fun ProfileScreenRoot(
    bottomPadding: Dp,
    lazyListState: LazyListState = rememberLazyListState(),
    onNavigateTo: (route: Route) -> Unit,
    userState: () -> UserState,
    uiEvent: (UiEvent) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    ProfileScreen(
        modifier = Modifier.padding(bottom = bottomPadding),
        lazyListState = lazyListState,
        userState = userState,
        profileUiEvent = { event ->
            when (event) {
                is ProfileUiEvent.OnNavigateTo -> onNavigateTo(event.route)
                ProfileUiEvent.CreateRequestToken -> uiEvent(UiEvent.CreateRequestToken)
                ProfileUiEvent.DropRequestToken -> uiEvent(UiEvent.DropRequestToken)
            }
        }
    )
}

@Composable
private fun ProfileScreen(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    userState: () -> UserState,
    profileUiEvent: (ProfileUiEvent) -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(top = innerPadding.calculateTopPadding() + 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = lazyListState
        ) {
            item(
                contentType = { "User info" }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(MaterialTheme.shapes.large)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = .1f),
                            shape = MaterialTheme.shapes.large
                        )
                ) {
                    ProfileBox(
                        userState = userState,
                        profileUiEvent = profileUiEvent
                    )
                }
            }
            item(
                contentType = { "Content" }
            ) {
                Column {
                    AnimatedVisibility(visible = !userState().user?.sessionId.isNullOrEmpty()) {
                        Column {
                            CustomListItem(
                                onClick = { profileUiEvent(ProfileUiEvent.OnNavigateTo(Route.Watchlist)) },
                                leadingContent = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_bookmarks_fill0_wght400),
                                        contentDescription = stringResource(id = R.string.watchlist)
                                    )
                                },
                                headlineContent = {
                                    Text(text =   stringResource(id = R.string.watchlist))
                                },
                                trailingContent = {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                        contentDescription = null
                                    )
                                }
                            )
                            CustomListItem(
                                onClick = { profileUiEvent(ProfileUiEvent.OnNavigateTo(Route.MyLists)) },
                                leadingContent = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_event_list_fill0_wght400),
                                        contentDescription = stringResource(id = R.string.my_lists)
                                    )
                                },
                                headlineContent = {
                                    Text(text =  stringResource(id = R.string.my_lists))
                                },
                                trailingContent = {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                        contentDescription = null
                                    )
                                }
                            )
                            CustomListItem(
                                onClick = { profileUiEvent(ProfileUiEvent.OnNavigateTo(Route.Favorite)) },
                                leadingContent = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_favorite_fill0_wght400),
                                        contentDescription = stringResource(id = R.string.favorite)
                                    )
                                },
                                headlineContent = {
                                    Text(text = stringResource(id = R.string.favorite))
                                },
                                trailingContent = {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                        contentDescription = null
                                    )
                                }
                            )
                        }
                    }
                    CustomListItem(
                        onClick = { profileUiEvent(ProfileUiEvent.OnNavigateTo(Route.Settings)) },
                        leadingContent = {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_settings_fill0_wght400),
                                contentDescription = stringResource(id = R.string.settings)
                            )
                        },
                        headlineContent = {
                            Text(text = stringResource(id = R.string.settings))
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface.copy(.7f))
                .statusBarsPadding()
        )
    }
}
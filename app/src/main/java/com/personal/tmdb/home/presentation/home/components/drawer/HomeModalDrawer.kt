package com.personal.tmdb.home.presentation.home.components.drawer

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.UiEvent
import com.personal.tmdb.UserState
import com.personal.tmdb.core.navigation.RootNavGraph
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.util.ApplyStatusBarsTheme
import com.personal.tmdb.core.util.C
import com.personal.tmdb.ui.theme.backgroundLight
import com.personal.tmdb.ui.theme.outlineVariantLight
import com.personal.tmdb.ui.theme.tmdbLightPurple
import com.personal.tmdb.ui.theme.tmdbProfile

@Composable
fun HomeModalDrawer(
    onNavigateTo: (route: String) -> Unit,
    drawerState: DrawerState,
    closeDrawer: () -> Unit,
    preferencesState: State<PreferencesState>,
    userState: State<UserState>,
    uiEvent: (UiEvent) -> Unit
) {
    if (drawerState.targetValue == DrawerValue.Open) {
        BackHandler {
            closeDrawer()
        }
    }
    if (drawerState.targetValue == DrawerValue.Closed)
        ApplyStatusBarsTheme(applyLightStatusBars = preferencesState.value.darkTheme ?: isSystemInDarkTheme())
    else
        ApplyStatusBarsTheme(applyLightStatusBars = if (userState.value.sessionId.isNullOrEmpty()) preferencesState.value.darkTheme ?: isSystemInDarkTheme() else true)
    ModalDrawerSheet(
        drawerContainerColor = MaterialTheme.colorScheme.background,
        drawerContentColor = MaterialTheme.colorScheme.onBackground,
        windowInsets = WindowInsets(top = 0.dp)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                val radialGradient by remember {
                    mutableStateOf(
                        object : ShaderBrush() {
                            override fun createShader(size: Size): Shader {
                                val biggerDimension = maxOf(size.height, size.width)
                                return RadialGradientShader(
                                    colors = listOf(tmdbLightPurple.copy(alpha = .3f), tmdbLightPurple.copy(alpha = 0f)),
                                    center = Offset(size.width, size.height),
                                    radius = biggerDimension
                                )
                            }
                        }
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if (userState.value.sessionId.isNullOrEmpty()) {
                                Modifier
                                    .background(MaterialTheme.colorScheme.surfaceContainer)
                            } else {
                                Modifier
                                    .background(tmdbProfile)
                                    .background(radialGradient)
                            }
                        )
                        .height(IntrinsicSize.Min)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            modifier = Modifier.matchParentSize(),
                            painter = painterResource(id = R.drawable.pipes_pink),
                            contentDescription = "Profile Background",
                            contentScale = ContentScale.Crop
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                closeDrawer()
                                if (!userState.value.sessionId.isNullOrEmpty()) {
                                    onNavigateTo(RootNavGraph.SETTINGS)
                                } else {
                                    onNavigateTo(RootNavGraph.AUTH)
                                }
                            }
                            .statusBarsPadding()
                            .padding(start = 16.dp, top = 4.dp, end = 4.dp, bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(top = 12.dp)
                                .weight(1f, false),
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
                                if (userState.value.sessionId.isNullOrEmpty()) {
                                    Icon(
                                        modifier = Modifier.size(28.dp),
                                        painter = painterResource(id = R.drawable.icon_login_fill0_wght400),
                                        contentDescription = stringResource(id = R.string.login)
                                    )
                                } else {
                                    AsyncImage(
                                        modifier = Modifier.fillMaxSize(),
                                        model = if (userState.value.userInfo?.tmdbAvatarPath == null)
                                            C.GRAVATAR_IMAGES_BASE_URL + userState.value.userInfo?.gravatarAvatarPath + "?s=185"
                                        else
                                            C.TMDB_IMAGES_BASE_URL + C.PROFILE_W185 + userState.value.userInfo?.tmdbAvatarPath,
                                        placeholder = painterResource(id = R.drawable.placeholder),
                                        error = painterResource(id = R.drawable.placeholder),
                                        contentDescription = "Avatar",
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                            Column {
                                if (userState.value.sessionId.isNullOrEmpty()) {
                                    Text(
                                        text = stringResource(id = R.string.login),
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Medium
                                    )
                                } else {
                                    if (userState.value.userInfo?.name.isNullOrEmpty()) {
                                        Text(
                                            text = userState.value.userInfo?.username ?: "",
                                            style = MaterialTheme.typography.titleLarge,
                                            fontWeight = FontWeight.Medium,
                                            color = backgroundLight
                                        )
                                    } else {
                                        Text(
                                            text = userState.value.userInfo?.name ?: "",
                                            style = MaterialTheme.typography.titleLarge,
                                            fontWeight = FontWeight.Medium,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            color = backgroundLight
                                        )
                                        Text(
                                            text = userState.value.userInfo?.username ?: "",
                                            style = MaterialTheme.typography.bodyMedium,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            color = outlineVariantLight
                                        )
                                    }
                                }
                            }
                        }
                        AnimatedContent(
                            targetState = preferencesState.value.darkTheme,
                            label = "Swap icon button anim",
                            transitionSpec = {
                                slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start, initialOffset = { it }) + scaleIn() togetherWith
                                        slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start) + scaleOut()
                            }
                        ) { targetState ->
                            when (targetState) {
                                true -> {
                                    IconButton(onClick = { uiEvent(UiEvent.SetTheme(false)) }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.icon_light_mode_fill1_wght400),
                                            contentDescription = stringResource(id = R.string.light_mode),
                                            tint = if (userState.value.sessionId.isNullOrEmpty()) MaterialTheme.colorScheme.onBackground else backgroundLight
                                        )
                                    }
                                }
                                false -> {
                                    IconButton(onClick = { uiEvent(UiEvent.SetTheme(true)) }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.icon_dark_mode_fill1_wght400),
                                            contentDescription = stringResource(id = R.string.dark_mode),
                                            tint = if (userState.value.sessionId.isNullOrEmpty()) MaterialTheme.colorScheme.onBackground else backgroundLight
                                        )
                                    }
                                }
                                else -> {
                                    val darkTheme = isSystemInDarkTheme()
                                    IconButton(onClick = { uiEvent(UiEvent.SetTheme(!darkTheme)) }) {
                                        if (darkTheme) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.icon_light_mode_fill1_wght400),
                                                contentDescription = stringResource(id = R.string.light_mode),
                                                tint = if (userState.value.sessionId.isNullOrEmpty()) MaterialTheme.colorScheme.onBackground else backgroundLight
                                            )
                                        } else {
                                            Icon(
                                                painter = painterResource(id = R.drawable.icon_dark_mode_fill1_wght400),
                                                contentDescription = stringResource(id = R.string.dark_mode),
                                                tint = if (userState.value.sessionId.isNullOrEmpty()) MaterialTheme.colorScheme.onBackground else backgroundLight
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            item {
                if (!userState.value.sessionId.isNullOrEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                closeDrawer()
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_bookmarks_fill0_wght400),
                                contentDescription = stringResource(id = R.string.watchlist)
                            )
                            Text(
                                text = stringResource(id = R.string.watchlist),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                }
                if (!userState.value.sessionId.isNullOrEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                closeDrawer()
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_event_list_fill0_wght400),
                                contentDescription = stringResource(id = R.string.lists)
                            )
                            Text(
                                text = stringResource(id = R.string.lists),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            closeDrawer()
                            onNavigateTo(RootNavGraph.SETTINGS)
                        }
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
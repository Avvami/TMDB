package com.personal.tmdb.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import coil3.compose.AsyncImage
import com.personal.tmdb.R
import com.personal.tmdb.UserState
import com.personal.tmdb.core.navigation.NavigationItem
import com.personal.tmdb.core.navigation.Route
import com.personal.tmdb.core.util.C

@Composable
fun BottomBar(
    rootNavController: NavController,
    userState: State<UserState>,
    navBarItemReselect: (() -> Unit)?
) {
    val navigationItems = remember(userState.value.sessionId) {
        buildNavigationItems(userState)
    }
    val bottomBarVisibilityState = bottomBarVisibility(navController = rootNavController)

    AnimatedVisibility(
        visible = bottomBarVisibilityState.value,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
    ) {
        CustomNavigationBar {
            val navBackStackEntry by rootNavController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            navigationItems.fastForEach { item ->
                val isSelected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true
                CustomNavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        if (isSelected) {
                            navBarItemReselect?.invoke()
                        } else {
                            rootNavController.navigate(item.route) {
                                popUpTo(rootNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        if (isSelected) item.selectedIcon() else item.unselectedIcon()
                    },
                    label = {
                        Text(text = stringResource(item.label))
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onSurface,
                        selectedTextColor = MaterialTheme.colorScheme.onSurface,
                        indicatorColor = Color.Transparent,
                        unselectedIconColor = MaterialTheme.colorScheme.surfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            }
        }
    }
}

fun buildNavigationItems(userState: State<UserState>): List<NavigationItem> {
    return buildList {
        add(
            NavigationItem(
                label = R.string.home,
                unselectedIcon = {
                    Icon(
                        painter = painterResource(R.drawable.icon_home_fill0_wght400),
                        contentDescription = stringResource(R.string.home)
                    )
                },
                selectedIcon = {
                    Icon(
                        painter = painterResource(R.drawable.icon_home_fill1_wght400),
                        contentDescription = stringResource(R.string.home)
                    )
                },
                route = Route.Home
            )
        )
        add(
            NavigationItem(
                label = R.string.search,
                unselectedIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = stringResource(R.string.search)
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = stringResource(R.string.search)
                    )
                },
                route = Route.Search
            )
        )
        add(
            NavigationItem(
                label = if (userState.value.sessionId.isNullOrEmpty()) R.string.profile else R.string.me,
                unselectedIcon = {
                    ProfileIcon(isSelected = false, userState = userState)
                },
                selectedIcon = {
                    ProfileIcon(isSelected = true, userState = userState)
                },
                route = Route.Profile()
            )
        )
    }
}

@Composable
fun ProfileIcon(isSelected: Boolean, userState: State<UserState>) {
    if (userState.value.sessionId.isNullOrEmpty()) {
        Icon(
            painter = painterResource(
                if (isSelected) R.drawable.icon_person_fill1_wght400 else R.drawable.icon_person_fill0_wght400
            ),
            contentDescription = stringResource(R.string.profile)
        )
    } else {
        val imageUrl = if (userState.value.userInfo?.tmdbAvatarPath == null) {
            C.GRAVATAR_IMAGES_BASE_URL.format(userState.value.userInfo?.gravatarAvatarPath)
        } else {
            C.TMDB_IMAGES_BASE_URL + C.PROFILE_W185 + userState.value.userInfo?.tmdbAvatarPath
        }

        AsyncImage(
            model = imageUrl,
            placeholder = painterResource(R.drawable.placeholder),
            error = painterResource(R.drawable.placeholder),
            contentDescription = stringResource(R.string.me),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .then(
                    if (isSelected) Modifier.border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface,
                        shape = CircleShape
                    ) else Modifier
                )
        )
    }
}

@Composable
fun bottomBarVisibility(
    navController: NavController
): MutableState<Boolean> {

    val bottomBarVisibilityState = rememberSaveable { (mutableStateOf(false)) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    when {
//        currentDestination?.hasRoute(Route.Profile::class) == true -> bottomBarVisibilityState.value = false
        else -> bottomBarVisibilityState.value = true
    }

    return bottomBarVisibilityState
}
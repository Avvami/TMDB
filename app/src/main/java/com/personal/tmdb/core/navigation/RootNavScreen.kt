package com.personal.tmdb.core.navigation

import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import coil3.compose.AsyncImage
import com.personal.tmdb.MainViewModel
import com.personal.tmdb.R
import com.personal.tmdb.UiEvent
import com.personal.tmdb.UserState
import com.personal.tmdb.auth.presentation.auth.AuthScreen
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.CustomNavigationBar
import com.personal.tmdb.core.presentation.components.CustomNavigationBarItem
import com.personal.tmdb.core.presentation.components.animatedComposable
import com.personal.tmdb.core.util.C
import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.detail.presentation.cast.CastScreen
import com.personal.tmdb.detail.presentation.collection.CollectionScreen
import com.personal.tmdb.detail.presentation.detail.DetailScreen
import com.personal.tmdb.detail.presentation.episode.EpisodeDetailsScreen
import com.personal.tmdb.detail.presentation.episodes.EpisodesScreen
import com.personal.tmdb.detail.presentation.image.ImageViewerScreen
import com.personal.tmdb.detail.presentation.person.PersonScreen
import com.personal.tmdb.detail.presentation.reviews.ReviewsScreen
import com.personal.tmdb.home.presentation.home.HomeScreen
import com.personal.tmdb.search.presentation.search.SearchScreen
import com.personal.tmdb.settings.presentation.appearance.AppearanceScreen
import com.personal.tmdb.settings.presentation.settings.SettingsScreen

@Composable
fun RootNavigationScreen(
    mainViewModel: MainViewModel,
    preferencesState: State<PreferencesState>,
    userState: State<UserState>
) {
    val graphs by remember {
        derivedStateOf {
            buildList {
                add(
                    NavigationItem(
                        label = R.string.home,
                        unselectedIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_home_fill0_wght400),
                                contentDescription = stringResource(id = R.string.home)
                            )
                        },
                        selectedIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_home_fill1_wght400),
                                contentDescription = stringResource(id = R.string.home)
                            )
                        },
                        route = RootNavGraph.HOME_GRAPH
                    )
                )
                add(
                    NavigationItem(
                        label = R.string.search,
                        unselectedIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = stringResource(id = R.string.search)
                            )
                        },
                        selectedIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = stringResource(id = R.string.search)
                            )
                        },
                        route = RootNavGraph.SEARCH_GRAPH
                    )
                )
                if (false) {
                    add(
                        NavigationItem(
                            label = R.string.watchlist,
                            unselectedIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_bookmarks_fill0_wght400),
                                    contentDescription = stringResource(id = R.string.watchlist)
                                )
                            },
                            selectedIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_bookmarks_fill1_wght400),
                                    contentDescription = stringResource(id = R.string.watchlist)
                                )
                            },
                            route = RootNavGraph.WATCHLIST_GRAPH
                        )
                    )
                }
                add(
                    NavigationItem(
                        label = if (userState.value.sessionId.isNullOrEmpty()) R.string.profile else R.string.me,
                        unselectedIcon = {
                            if (userState.value.sessionId.isNullOrEmpty()) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_person_fill0_wght400),
                                    contentDescription = stringResource(id = R.string.profile)
                                )
                            } else {
                                AsyncImage(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape),
                                    model = if (userState.value.userInfo?.tmdbAvatarPath == null)
                                        C.GRAVATAR_IMAGES_BASE_URL.format(userState.value.userInfo?.gravatarAvatarPath)
                                    else
                                        C.TMDB_IMAGES_BASE_URL + C.PROFILE_W185 + userState.value.userInfo?.tmdbAvatarPath,
                                    placeholder = painterResource(id = R.drawable.placeholder),
                                    error = painterResource(id = R.drawable.placeholder),
                                    contentDescription = stringResource(id = R.string.me),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        },
                        selectedIcon = {
                            if (userState.value.sessionId.isNullOrEmpty()) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_person_fill1_wght400),
                                    contentDescription = stringResource(id = R.string.profile)
                                )
                            } else {
                                AsyncImage(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .border(
                                            width = 1.dp,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            shape = CircleShape
                                        ),
                                    model = if (userState.value.userInfo?.tmdbAvatarPath == null)
                                        C.GRAVATAR_IMAGES_BASE_URL.format(userState.value.userInfo?.gravatarAvatarPath)
                                    else
                                        C.TMDB_IMAGES_BASE_URL + C.PROFILE_W185 + userState.value.userInfo?.tmdbAvatarPath,
                                    placeholder = painterResource(id = R.drawable.placeholder),
                                    error = painterResource(id = R.drawable.placeholder),
                                    contentDescription = stringResource(id = R.string.me),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        },
                        route = RootNavGraph.PROFILE_GRAPH
                    )
                )
            }
        }
    }
    val rootNavController = rememberNavController()
    val navBackStackEntry by rootNavController.currentBackStackEntryAsState()
    val navigationBarState = bottomBarVisibility(navController = rootNavController)
    val childNavControllers = remember { mutableStateMapOf<String, NavController>() }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = navigationBarState.value,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
            ) {
                CustomNavigationBar {
                    graphs.fastForEach { graph ->
                        val isSelected = graph.route == navBackStackEntry?.destination?.route
                        CustomNavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                if (isSelected) {
                                    childNavControllers[graph.route]?.let { childNavController ->
                                        childNavController.popBackStack(
                                            destinationId = childNavController.graph.startDestinationId,
                                            inclusive = false
                                        )
                                    }
                                } else {
                                    rootNavController.navigate(graph.route) {
                                        popUpTo(rootNavController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = {
                                AnimatedContent(
                                    targetState = isSelected,
                                    label = "Icon animation",
                                    transitionSpec = { scaleIn(initialScale = .5f) + fadeIn() togetherWith scaleOut(targetScale = .5f) + fadeOut() }
                                ) { targetState ->
                                    if (targetState) graph.selectedIcon() else graph.unselectedIcon()
                                }
                            },
                            label = {
                                Text(text = stringResource(graph.label))
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
    ) { paddingValues ->
        NavHost(
            navController = rootNavController,
            startDestination = RootNavGraph.HOME_GRAPH
        ) {
            composable(RootNavGraph.HOME_GRAPH) {
                val childNavController = rememberNavController()
                childNavControllers[RootNavGraph.HOME_GRAPH] = childNavController
                ChildNavGraph(
                    navController = childNavController,
                    startDestination = RootNavGraph.HOME,
                    bottomPadding = paddingValues.calculateBottomPadding(),
                    mainViewModel = mainViewModel,
                    preferencesState = preferencesState,
                    userState = userState
                )
            }
            composable(RootNavGraph.SEARCH_GRAPH) {
                val childNavController = rememberNavController()
                childNavControllers[RootNavGraph.SEARCH_GRAPH] = childNavController
                ChildNavGraph(
                    navController = childNavController,
                    startDestination = RootNavGraph.SEARCH + "/${MediaType.MULTI.name.lowercase()}",
                    bottomPadding = paddingValues.calculateBottomPadding(),
                    mainViewModel = mainViewModel,
                    preferencesState = preferencesState,
                    userState = userState
                )
            }
            if (false) {
                composable(RootNavGraph.WATCHLIST_GRAPH) {
                    val childNavController = rememberNavController()
                    childNavControllers[RootNavGraph.WATCHLIST_GRAPH] = childNavController
                    ChildNavGraph(
                        navController = childNavController,
                        startDestination = RootNavGraph.WATCHLIST,
                        bottomPadding = paddingValues.calculateBottomPadding(),
                        mainViewModel = mainViewModel,
                        preferencesState = preferencesState,
                        userState = userState
                    )
                }
            }
            composable(RootNavGraph.PROFILE_GRAPH) {
                val childNavController = rememberNavController()
                childNavControllers[RootNavGraph.PROFILE_GRAPH] = childNavController
                ChildNavGraph(
                    navController = childNavController,
                    startDestination = RootNavGraph.PROFILE,
                    bottomPadding = paddingValues.calculateBottomPadding(),
                    mainViewModel = mainViewModel,
                    preferencesState = preferencesState,
                    userState = userState
                )
            }
        }
    }
}

@Composable
fun ChildNavGraph(
    navController: NavHostController,
    startDestination: String,
    bottomPadding: Dp,
    mainViewModel: MainViewModel,
    preferencesState: State<PreferencesState>,
    userState: State<UserState>
) {
    val onNavigateBack: () -> Unit = {
        navController.navigateUp()
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        animatedComposable(
            route = RootNavGraph.HOME,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "${C.REDIRECT_URL}/{${C.APPROVED}}"; action = Intent.ACTION_VIEW
                }
            ),
            arguments = listOf(
                navArgument(C.APPROVED) { type = NavType.StringType; nullable = true }
            )
        ) { entry ->
            LaunchedEffect(key1 = true) {
                if (entry.arguments?.getString(C.APPROVED)?.toBooleanStrictOrNull() == true) {
                    mainViewModel.uiEvent(UiEvent.SignInUser)
                }
            }
            HomeScreen(
                bottomPadding = bottomPadding,
                onNavigateTo = { route ->
                    navController.navigate(route = route) {
                        launchSingleTop = true
                    }
                },
                preferencesState = preferencesState,
                userState = userState,
                uiEvent = mainViewModel::uiEvent
            )
        }
        animatedComposable(
            route = RootNavGraph.AUTH
        ) {
            AuthScreen(
                navigateBack = onNavigateBack,
                preferencesState = preferencesState,
                userState = userState,
                uiEvent = mainViewModel::uiEvent
            )
        }
        animatedComposable(
            route = RootNavGraph.SETTINGS
        ) {
            SettingsScreen(
                navigateBack = onNavigateBack,
                onNavigateTo = { route ->
                    navController.navigate(route = route) {
                        launchSingleTop = true
                    }
                },
                preferencesState = preferencesState,
                userState = userState,
                uiEvent = mainViewModel::uiEvent
            )
        }
        animatedComposable(
            route = RootNavGraph.DETAIL + "/{${C.MEDIA_TYPE}}/{${C.MEDIA_ID}}",
            arguments = listOf(
                navArgument(C.MEDIA_TYPE) { type = NavType.StringType; nullable = false },
                navArgument(C.MEDIA_ID) { type = NavType.IntType; nullable = false }
            )
        ) {
            DetailScreen(
                navigateBack = onNavigateBack,
                onNavigateTo = { route ->
                    navController.navigate(route = route)
                },
                navigateToHome = {},
                preferencesState = preferencesState,
                userState = userState
            )
        }
        animatedComposable(
            route = RootNavGraph.SEARCH + "/{${C.SEARCH_TYPE}}?${C.SEARCH_QUERY}={${C.SEARCH_QUERY}}",
            arguments = listOf(
                navArgument(C.SEARCH_TYPE) { type = NavType.StringType; nullable = false },
                navArgument(C.SEARCH_QUERY) { type = NavType.StringType; nullable = true; defaultValue = null }
            )
        ) {
            SearchScreen(
                bottomPadding = bottomPadding,
                onNavigateTo = { route ->
                    navController.navigate(route = route) {
                        launchSingleTop = true
                    }
                },
                preferencesState = preferencesState
            )
        }
        animatedComposable(
            route = RootNavGraph.COLLECTION + "/{${C.COLLECTION_ID}}",
            arguments = listOf(
                navArgument(C.COLLECTION_ID) { type = NavType.IntType; nullable = false }
            )
        ) {
            CollectionScreen(
                navigateBack = onNavigateBack,
                onNavigateTo = { route ->
                    navController.navigate(route = route) {
                        launchSingleTop = true
                    }
                },
                navigateToHome = {},
                preferencesState = preferencesState
            )
        }
        animatedComposable(
            route = RootNavGraph.CAST + "/{${C.MEDIA_NAME}}/{${C.MEDIA_TYPE}}/{${C.MEDIA_ID}}" +
                    "?${C.SEASON_NUMBER}={${C.SEASON_NUMBER}}?${C.EPISODE_NUMBER}={${C.EPISODE_NUMBER}}",
            arguments = listOf(
                navArgument(C.MEDIA_NAME) { type = NavType.StringType; nullable = false },
                navArgument(C.MEDIA_TYPE) { type = NavType.StringType; nullable = false },
                navArgument(C.MEDIA_ID) { type = NavType.IntType; nullable = false },
                navArgument(C.SEASON_NUMBER) { type = NavType.StringType; nullable = true },
                navArgument(C.EPISODE_NUMBER) { type = NavType.StringType; nullable = true }
            )
        ) {
            CastScreen(
                navigateBack = onNavigateBack,
                onNavigateTo = { route ->
                    navController.navigate(route = route) {
                        launchSingleTop = true
                    }
                },
                navigateToHome = {}
            )
        }
        animatedComposable(
            route = RootNavGraph.PERSON + "/{${C.PERSON_NAME}}/{${C.PERSON_ID}}",
            arguments = listOf(
                navArgument(C.PERSON_NAME) { type = NavType.StringType; nullable = false },
                navArgument(C.PERSON_ID) { type = NavType.IntType; nullable = false }
            )
        ) {
            PersonScreen(
                navigateBack = onNavigateBack,
                onNavigateTo = { route ->
                    navController.navigate(route = route) {
                        launchSingleTop = true
                    }
                },
                navigateToHome = {},
                preferencesState = preferencesState
            )
        }
        animatedComposable(
            route = RootNavGraph.APPEARANCE
        ) {
            AppearanceScreen(
                navigateBack = onNavigateBack,
                preferencesState = preferencesState,
                uiEvent = mainViewModel::uiEvent
            )
        }
        animatedComposable(
            route = RootNavGraph.EPISODES + "/{${C.MEDIA_ID}}/{${C.SEASON_NUMBER}}",
            arguments = listOf(
                navArgument(C.MEDIA_ID) { type = NavType.IntType; nullable = false },
                navArgument(C.SEASON_NUMBER) { type = NavType.IntType; nullable = false }
            )
        ) {
            EpisodesScreen(
                navigateBack = onNavigateBack,
                onNavigateTo = { route ->
                    navController.navigate(route = route) {
                        launchSingleTop = true
                    }
                },
                navigateToHome = {},
                preferencesState = preferencesState
            )
        }
        animatedComposable(
            route = RootNavGraph.EPISODE + "/{${C.MEDIA_ID}}/{${C.SEASON_NUMBER}}/{${C.EPISODE_NUMBER}}",
            arguments = listOf(
                navArgument(C.MEDIA_ID) { type = NavType.IntType; nullable = false },
                navArgument(C.SEASON_NUMBER) { type = NavType.IntType; nullable = false },
                navArgument(C.EPISODE_NUMBER) { type = NavType.IntType; nullable = false }
            )
        ) {
            EpisodeDetailsScreen(
                navigateBack = onNavigateBack,
                onNavigateTo = { route ->
                    navController.navigate(route = route) {
                        launchSingleTop = true
                    }
                },
                navigateToHome = {},
                userState = userState
            )
        }
        animatedComposable(
            route = RootNavGraph.IMAGE + "/{${C.IMAGE_TYPE}}/{${C.IMAGES_PATH}}?${C.IMAGE_INDEX}={${C.IMAGE_INDEX}}",
            arguments = listOf(
                navArgument(C.IMAGE_TYPE) { type = NavType.StringType; nullable = false },
                navArgument(C.IMAGES_PATH) { type = NavType.StringType; nullable = false },
                navArgument(C.IMAGE_INDEX) { type = NavType.StringType; nullable = true }
            )
        ) {
            ImageViewerScreen(
                navigateBack = onNavigateBack,
                preferencesState = preferencesState
            )
        }
        animatedComposable(
            route = RootNavGraph.REVIEWS + "/{${C.MEDIA_TYPE}}/{${C.MEDIA_ID}}",
            arguments = listOf(
                navArgument(C.MEDIA_TYPE) { type = NavType.StringType; nullable = false },
                navArgument(C.MEDIA_ID) { type = NavType.IntType; nullable = false }
            )
        ) {
            ReviewsScreen(
                navigateBack = onNavigateBack,
                navigateToHome = {},
                preferencesState = preferencesState
            )
        }
    }
}

object RootNavGraph {
    const val HOME_GRAPH = "home_graph"
    const val SEARCH_GRAPH = "search_graph"
    const val WATCHLIST_GRAPH = "watchlist_graph"
    const val PROFILE_GRAPH = "profile_graph"

    const val PROFILE = "profile_screen"
    const val HOME = "home_screen"
    const val AUTH = "auth_screen"
    const val SETTINGS = "settings_screen"
    const val DETAIL = "detail_screen"
    const val SEARCH = "search_screen"
    const val COLLECTION = "collection_screen"
    const val CAST = "cast_screen"
    const val PERSON = "person_screen"
    const val APPEARANCE = "appearance_screen"
    const val EPISODES = "episodes_screen"
    const val EPISODE = "episode_screen"
    const val IMAGE = "image_viewer_screen"
    const val REVIEWS = "reviews_screen"
    const val WATCHLIST = "watchlist_screen"
    const val FAVORITE = "favorite_screen"
}

@Composable
fun bottomBarVisibility(
    navController: NavController
): MutableState<Boolean> {

    val bottomBarVisibilityState = rememberSaveable { (mutableStateOf(false)) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    when (navBackStackEntry?.destination?.route) {
        RootNavGraph.IMAGE + "/{${C.IMAGE_TYPE}}/{${C.IMAGES_PATH}}?${C.IMAGE_INDEX}={${C.IMAGE_INDEX}}" -> bottomBarVisibilityState.value = false
        else -> bottomBarVisibilityState.value = true
    }

    return bottomBarVisibilityState
}

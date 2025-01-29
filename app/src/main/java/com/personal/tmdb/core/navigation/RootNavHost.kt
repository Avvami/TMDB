package com.personal.tmdb.core.navigation

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.personal.tmdb.UiEvent
import com.personal.tmdb.UserState
import com.personal.tmdb.auth.presentation.auth.AuthScreen
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.BottomBar
import com.personal.tmdb.core.presentation.components.animatedComposable
import com.personal.tmdb.core.util.C
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
import kotlinx.coroutines.launch

@Composable
fun RootNavHost(
    preferencesState: State<PreferencesState>,
    userState: State<UserState>,
    uiEvent: (UiEvent) -> Unit
) {
    val scope = rememberCoroutineScope()
    val rootNavController = rememberNavController()
    var navBarItemReselect: (() -> Unit)? by remember { mutableStateOf(null) }

    Scaffold(
        bottomBar = {
            BottomBar(
                rootNavController = rootNavController,
                userState = userState,
                navBarItemReselect = navBarItemReselect
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = rootNavController,
            startDestination = Route.Home
        ) {
            val bottomBarPadding = innerPadding.calculateBottomPadding()
            composable<Route.Home> {
                val lazyListState = rememberLazyListState()
                val homeNavController = rememberNavController()
                navBarItemReselect = {
                    val popped = homeNavController.popBackStack(
                        destinationId = homeNavController.graph.startDestinationId,
                        inclusive = false
                    )
                    if (!popped && lazyListState.canScrollBackward) {
                        scope.launch {
                            lazyListState.animateScrollToItem(0)
                        }
                    }
                }
                ChildNavHost(
                    navController = homeNavController,
                    scrollState = lazyListState,
                    startDestination = Route.Home,
                    bottomBarPadding = bottomBarPadding,
                    preferencesState = preferencesState,
                    userState = userState,
                    uiEvent = uiEvent
                )
            }
            composable<Route.Search> {
                val lazyGridState = rememberLazyGridState()
                val searchNavController = rememberNavController()
                navBarItemReselect = {
                    val popped = searchNavController.popBackStack(
                        destinationId = searchNavController.graph.startDestinationId,
                        inclusive = false
                    )
                    if (!popped && lazyGridState.canScrollBackward) {
                        scope.launch {
                            lazyGridState.animateScrollToItem(0)
                        }
                    }
                }
                ChildNavHost(
                    navController = searchNavController,
                    scrollState = lazyGridState,
                    startDestination = Route.Search,
                    bottomBarPadding = bottomBarPadding,
                    preferencesState = preferencesState,
                    userState = userState,
                    uiEvent = uiEvent
                )
            }
            composable<Route.Profile> {
                val lazyListState = rememberLazyListState()
                val profileNavController = rememberNavController()
                navBarItemReselect = {
                    val popped = profileNavController.popBackStack(
                        destinationId = profileNavController.graph.startDestinationId,
                        inclusive = false
                    )
                    if (!popped && lazyListState.canScrollBackward) {
                        scope.launch {
                            lazyListState.animateScrollToItem(0)
                        }
                    }
                }
                ChildNavHost(
                    navController = profileNavController,
                    scrollState = lazyListState,
                    startDestination = Route.Profile(null),
                    bottomBarPadding = bottomBarPadding,
                    preferencesState = preferencesState,
                    userState = userState,
                    uiEvent = uiEvent
                )
            }
        }
    }
}

@Composable
fun ChildNavHost(
    navController: NavHostController,
    scrollState: Any,
    startDestination: Any,
    bottomBarPadding: Dp,
    preferencesState: State<PreferencesState>,
    userState: State<UserState>,
    uiEvent: (UiEvent) -> Unit
) {
    val onNavigateBack: () -> Unit = {
        navController.navigateUp()
    }
    val onNavigateTo: (route: Route) -> Unit = { route ->
        navController.navigate(route = route) {
            launchSingleTop = true
        }
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        animatedComposable<Route.Home> {
            HomeScreen(
                bottomPadding = bottomBarPadding,
                lazyListState = scrollState as LazyListState,
                onNavigateTo = {},
                preferencesState = preferencesState,
                userState = userState,
                uiEvent = uiEvent
            )
        }
        animatedComposable<Route.Search> {
            SearchScreen(
                bottomPadding = bottomBarPadding,
                lazyGridState = scrollState as LazyGridState,
                onNavigateTo = {},
                preferencesState = preferencesState
            )
        }
        animatedComposable<Route.Profile>(
            deepLinks = listOf(
                navDeepLink<Route.Profile>(
                    basePath = C.REDIRECT_URL
                )
            )
        ) {
            val approved = it.toRoute<Route.Profile>().approved
            LaunchedEffect(key1 = true) {
                if (approved == true) {
                    uiEvent(UiEvent.SignInUser)
                }
            }
            SearchScreen(
                bottomPadding = bottomBarPadding,
                onNavigateTo = {},
                preferencesState = preferencesState
            )
        }

        animatedComposable(
            route = RootNavGraph.AUTH
        ) {
            AuthScreen(
                navigateBack = onNavigateBack,
                preferencesState = preferencesState,
                userState = userState,
                uiEvent = uiEvent
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
                uiEvent = uiEvent
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
                uiEvent = uiEvent
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
    const val AUTH = "auth_screen"
    const val SETTINGS = "settings_screen"
    const val DETAIL = "detail_screen"
    const val COLLECTION = "collection_screen"
    const val CAST = "cast_screen"
    const val PERSON = "person_screen"
    const val APPEARANCE = "appearance_screen"
    const val EPISODES = "episodes_screen"
    const val EPISODE = "episode_screen"
    const val IMAGE = "image_viewer_screen"
    const val REVIEWS = "reviews_screen"
}

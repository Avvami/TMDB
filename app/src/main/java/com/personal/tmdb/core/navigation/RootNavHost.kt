package com.personal.tmdb.core.navigation

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.personal.tmdb.UiEvent
import com.personal.tmdb.UserState
import com.personal.tmdb.core.presentation.PreferencesState
import com.personal.tmdb.core.presentation.components.animatedComposable
import com.personal.tmdb.core.presentation.components.staticComposable
import com.personal.tmdb.core.util.C
import com.personal.tmdb.detail.presentation.cast.CastScreen
import com.personal.tmdb.detail.presentation.collection.CollectionScreen
import com.personal.tmdb.detail.presentation.detail.DetailScreenRoot
import com.personal.tmdb.detail.presentation.episode.EpisodeDetailsScreen
import com.personal.tmdb.detail.presentation.episodes.EpisodesScreenRoot
import com.personal.tmdb.detail.presentation.image.ImageViewerScreen
import com.personal.tmdb.detail.presentation.person.PersonScreen
import com.personal.tmdb.detail.presentation.reviews.ReviewsScreenRoot
import com.personal.tmdb.home.presentation.home.HomeScreenRoot
import com.personal.tmdb.profile.presentation.profile.ProfileScreenRoot
import com.personal.tmdb.search.presentation.search.SearchScreenRoot
import com.personal.tmdb.settings.presentation.appearance.AppearanceScreen
import com.personal.tmdb.settings.presentation.settings.SettingsScreen
import kotlinx.coroutines.launch

@Composable
fun RootNavHost(
    rootNavController: NavHostController,
    navBarItemReselect: ((() -> Unit)?) -> Unit,
    bottomBarPadding: Dp,
    preferencesState: State<PreferencesState>,
    userState: State<UserState>,
    uiEvent: (UiEvent) -> Unit
) {
    val scope = rememberCoroutineScope()

    NavHost(
        navController = rootNavController,
        startDestination = Route.Home::class
    ) {
        staticComposable<Route.Home> {
            val lazyListState = rememberLazyListState()
            val homeNavController = rememberNavController()
            navBarItemReselect {
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
        staticComposable<Route.Search> {
            val lazyGridState = rememberLazyGridState()
            val searchNavController = rememberNavController()
            navBarItemReselect {
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
        staticComposable<Route.Profile>(
            deepLinks = listOf(
                navDeepLink<Route.Profile>(
                    basePath = C.REDIRECT_URL
                )
            )
        ) {
            val approved = it.toRoute<Route.Profile>().approved
            val lazyListState = rememberLazyListState()
            val profileNavController = rememberNavController()
            navBarItemReselect {
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
                startDestination = Route.Profile(approved),
                bottomBarPadding = bottomBarPadding,
                preferencesState = preferencesState,
                userState = userState,
                uiEvent = uiEvent
            )
        }
    }
}

@Composable
fun ChildNavHost(
    navController: NavHostController,
    scrollState: Any,
    startDestination: Route,
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
            HomeScreenRoot(
                bottomPadding = bottomBarPadding,
                lazyListState = scrollState as LazyListState,
                onNavigateTo = onNavigateTo,
                preferencesState = { preferencesState.value }
            )
        }
        animatedComposable<Route.Search> {
            SearchScreenRoot(
                bottomPadding = bottomBarPadding,
                lazyGridState = scrollState as LazyGridState,
                onNavigateTo = onNavigateTo,
                preferencesState = preferencesState
            )
        }
        animatedComposable<Route.Profile> {
            val approved = it.toRoute<Route.Profile>().approved
            LaunchedEffect(key1 = true) {
                if (approved == true) {
                    uiEvent(UiEvent.SignInUser)
                }
            }
            ProfileScreenRoot(
                bottomPadding = bottomBarPadding,
                lazyListState = scrollState as LazyListState,
                onNavigateTo = {},
                preferencesState = { preferencesState.value },
                userState = { userState.value },
                uiEvent = uiEvent
            )
        }

        animatedComposable<Route.Detail> {
            DetailScreenRoot(
                bottomPadding = bottomBarPadding,
                onNavigateBack = onNavigateBack,
                onNavigateTo = onNavigateTo,
                preferencesState = { preferencesState.value },
                userState = { userState.value }
            )
        }
        animatedComposable<Route.Reviews> {
            ReviewsScreenRoot(
                bottomPadding = bottomBarPadding,
                onNavigateBack = onNavigateBack
            )
        }
        animatedComposable<Route.Episodes> {
            EpisodesScreenRoot(
                bottomPadding = bottomBarPadding,
                onNavigateBack = onNavigateBack,
                onNavigateTo = onNavigateTo
            )
        }
        animatedComposable<Route.Episode> {
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
        animatedComposable<Route.Collection> {
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
        animatedComposable<Route.Cast> {
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
        animatedComposable<Route.Person> {
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
        animatedComposable<Route.Image> {
            ImageViewerScreen(
                navigateBack = onNavigateBack,
                preferencesState = preferencesState
            )
        }
        animatedComposable<Route.Settings> {
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
        animatedComposable<Route.Appearance> {
            AppearanceScreen(
                navigateBack = onNavigateBack,
                preferencesState = preferencesState,
                uiEvent = uiEvent
            )
        }
    }
}

object RootNavGraph {
    const val AUTH = "auth_screen"
    const val SETTINGS = "settings_screen"
    const val DETAIL = "detail_screen"
    const val PERSON = "person_screen"
    const val APPEARANCE = "appearance_screen"
    const val IMAGE = "image_viewer_screen"
}

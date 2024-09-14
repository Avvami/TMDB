package com.personal.tmdb.core.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.personal.tmdb.MainViewModel
import com.personal.tmdb.UiEvent
import com.personal.tmdb.auth.presentation.auth.AuthScreen
import com.personal.tmdb.core.presentation.components.animatedComposable
import com.personal.tmdb.detail.presentation.image.ImageViewerScreen
import com.personal.tmdb.core.util.C
import com.personal.tmdb.detail.presentation.cast.CastScreen
import com.personal.tmdb.detail.presentation.collection.CollectionScreen
import com.personal.tmdb.detail.presentation.detail.DetailScreen
import com.personal.tmdb.detail.presentation.episode.EpisodeDetailsScreen
import com.personal.tmdb.detail.presentation.episodes.EpisodesScreen
import com.personal.tmdb.detail.presentation.person.PersonScreen
import com.personal.tmdb.detail.presentation.reviews.ReviewsScreen
import com.personal.tmdb.home.presentation.home.HomeScreen
import com.personal.tmdb.search.presentation.search.SearchScreen
import com.personal.tmdb.settings.presentation.appearance.AppearanceScreen
import com.personal.tmdb.settings.presentation.settings.SettingsScreen

@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val onNavigateBack: () -> Unit = {
        navController.navigateUp()
    }
    val navigateToHome: () -> Unit = {
        navController.navigate(RootNavGraph.HOME) {
            popUpTo(navController.graph.startDestinationId)
            launchSingleTop = true
        }
    }
    val preferencesState = mainViewModel.preferencesState.collectAsStateWithLifecycle()
    val userState = mainViewModel.userState.collectAsStateWithLifecycle()
    NavHost(
        navController = navController,
        route = RootNavGraph.ROOT,
        startDestination = RootNavGraph.HOME
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
                onNavigateTo = { route ->
                    navController.navigate(route = route) {
                        launchSingleTop = true
                    }
                },
                preferencesState = preferencesState,
                homeState = mainViewModel::homeState,
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
                navigateToHome = navigateToHome,
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
                navigateBack = onNavigateBack,
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
                navigateToHome = navigateToHome,
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
                navigateToHome = navigateToHome
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
                navigateToHome = navigateToHome,
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
                navigateToHome = navigateToHome,
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
                navigateToHome = navigateToHome,
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
                navigateToHome = navigateToHome,
                preferencesState = preferencesState
            )
        }
    }
}

object RootNavGraph {
    const val ROOT = "root_graph"

    const val HOME = "home_screen"
    const val AUTH = "auth_screen"
    const val SETTINGS = "settings_screen"
    const val DETAIL = "detail_screen"
    const val SEARCH = "detail_screen"
    const val COLLECTION = "collection_screen"
    const val CAST = "cast_screen"
    const val PERSON = "person_screen"
    const val APPEARANCE = "appearance_screen"
    const val EPISODES = "episodes_screen"
    const val EPISODE = "episode_screen"
    const val IMAGE = "image_viewer_screen"
    const val REVIEWS = "reviews_screen"
}
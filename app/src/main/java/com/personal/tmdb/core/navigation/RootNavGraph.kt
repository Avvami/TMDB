package com.personal.tmdb.core.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import com.personal.tmdb.MainViewModel
import com.personal.tmdb.auth.presentation.auth.AuthScreen
import com.personal.tmdb.core.presentation.components.animatedComposable
import com.personal.tmdb.detail.presentation.DetailScreen
import com.personal.tmdb.home.presentation.home.HomeScreen
import com.personal.tmdb.settings.presentation.settings.SettingsScreen

@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val onNavigateBack: () -> Unit = {
        with(navController) {
            this.navigateUp()
        }
    }
    NavHost(
        navController = navController,
        route = RootNavGraph.ROOT,
        startDestination = RootNavGraph.HOME
    ) {
        animatedComposable(
            route = RootNavGraph.HOME
        ) {
            HomeScreen(
                onNavigateTo = { route ->
                    navController.navigate(route = route) {
                        launchSingleTop = true
                    }
                },
                preferencesState = mainViewModel.preferencesState.collectAsStateWithLifecycle(),
                homeState = mainViewModel::homeState,
                uiEvent = mainViewModel::uiEvent
            )
        }
        animatedComposable(
            route = RootNavGraph.AUTH
        ) {
            AuthScreen(
                navigateBack = onNavigateBack
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
                uiEvent = mainViewModel::uiEvent
            )
        }
        animatedComposable(
            route = RootNavGraph.DETAIL + "/{mediaId}/{mediaType}",
            arguments = listOf(
                navArgument("mediaId") { type = NavType.IntType; nullable = false },
                navArgument("mediaType") { type = NavType.StringType; nullable = false}
            )
        ) {
            DetailScreen(
                navigateBack = onNavigateBack,
                onNavigateTo = { route ->
                    navController.navigate(route = route) {
                        launchSingleTop = true
                    }
                }
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
}

//val NavHostController.canGoBack: Boolean
//    get() = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED
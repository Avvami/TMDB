package com.personal.tmdb.core.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.personal.tmdb.MainViewModel
import com.personal.tmdb.auth.presentation.auth.AuthScreen
import com.personal.tmdb.home.presentation.home.HomeScreen

@Composable
fun RootNavigationGraph(
    navHostController: NavHostController,
    mainViewModel: MainViewModel
) {
    NavHost(
        navController = navHostController,
        route = RootNavGraph.ROOT,
        startDestination = RootNavGraph.HOME
    ) {
        composable(
            route = RootNavGraph.HOME
        ) {
            HomeScreen(
                navigateToAuthScreen = { if (navHostController.canGoBack) navHostController.navigate(RootNavGraph.AUTH) }
            )
        }
        composable(
            route = RootNavGraph.AUTH
        ) {
            AuthScreen(
                navigateBack = { if (navHostController.canGoBack) navHostController.popBackStack() }
            )
        }
    }
}

object RootNavGraph {
    const val ROOT = "root_graph"

    const val HOME = "home_screen"
    const val AUTH = "auth_screen"
}

val NavHostController.canGoBack: Boolean
    get() = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED
package com.personal.tmdb.core.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable

data class NavigationItem(
    @StringRes val label: Int,
    val unselectedIcon: @Composable () -> Unit,
    val selectedIcon: @Composable () -> Unit,
    val route: String
)
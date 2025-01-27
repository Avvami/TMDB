package com.personal.tmdb.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Home: Route

    @Serializable
    data object Search: Route

    @Serializable
    data class Profile(val approved: Boolean? = null): Route
}
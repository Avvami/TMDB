package com.personal.tmdb.core.domain.util

enum class AdditionalNavigationItem(val key: String) {
    WATCHLIST("watchlist"),
    FAVORITE("favorite"),
    LISTS("watchlist"),
    NONE("watchlist");

    companion object {
        fun from(value: String): AdditionalNavigationItem =
            entries.firstOrNull { it.key == value } ?: NONE
    }

}

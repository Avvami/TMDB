package com.personal.tmdb.core.domain.util

import com.personal.tmdb.R

enum class AdditionalNavigationItem(
    val key: String,
    val iconRes: Int,
    val selectedIconRes: Int
) {
    NONE("none", R.drawable.icon_block_fill0_wght400, R.drawable.icon_block_fill0_wght400),
    WATCHLIST("watchlist", R.drawable.icon_bookmarks_fill0_wght400, R.drawable.icon_bookmarks_fill1_wght400),
    LISTS("lists", R.drawable.icon_event_list_fill0_wght400, R.drawable.icon_event_list_fill1_wght400),
    FAVORITE("favorite", R.drawable.icon_favorite_fill0_wght400, R.drawable.icon_favorite_fill1_wght400);

    companion object {
        fun from(value: String): AdditionalNavigationItem =
            entries.firstOrNull { it.key == value } ?: NONE

        /*Using this because of how data stored in the database (lowercase letters)*/
        fun AdditionalNavigationItem.toUiText(): UiText =
            when (this) {
                NONE -> UiText.StringResource(R.string.none)
                WATCHLIST -> UiText.StringResource(R.string.watchlist)
                LISTS -> UiText.StringResource(R.string.my_lists)
                FAVORITE -> UiText.StringResource(R.string.favorite)
            }
    }
}

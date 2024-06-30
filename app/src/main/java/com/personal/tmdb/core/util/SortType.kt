package com.personal.tmdb.core.util

import com.personal.tmdb.R

enum class SortType {
    RATING_ASC,
    RATING_DESC,
    RELEASE_DATE_ASC,
    RELEASE_DATE_DESC,
}

fun convertSortType(sortType: SortType?): Int {
    return when (sortType) {
        SortType.RATING_ASC -> R.string.rating
        SortType.RATING_DESC -> R.string.rating
        SortType.RELEASE_DATE_ASC -> R.string.release_date
        SortType.RELEASE_DATE_DESC -> R.string.release_date
        null -> R.string.empty
    }
}
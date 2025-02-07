package com.personal.tmdb.detail.domain.util

import com.personal.tmdb.R
import com.personal.tmdb.core.domain.util.UiText

enum class CollectionSortType {
    RATING_ASC,
    RATING_DESC,
    RELEASE_DATE_ASC,
    RELEASE_DATE_DESC,
}

fun convertCollectionSortType(sortType: CollectionSortType): UiText {
    return when (sortType) {
        CollectionSortType.RATING_ASC -> UiText.StringResource(R.string.rating)
        CollectionSortType.RATING_DESC -> UiText.StringResource(R.string.rating)
        CollectionSortType.RELEASE_DATE_ASC -> UiText.StringResource(R.string.release_date)
        CollectionSortType.RELEASE_DATE_DESC -> UiText.StringResource(R.string.release_date)
    }
}
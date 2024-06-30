package com.personal.tmdb.detail.presentation.collection

import com.personal.tmdb.core.util.SortType

sealed interface CollectionUiEvent {
    data object ChangeCollapsedOverview: CollectionUiEvent
    data object ChangeDropdownState: CollectionUiEvent
    data class SetSortType(val sortType: SortType?): CollectionUiEvent
}
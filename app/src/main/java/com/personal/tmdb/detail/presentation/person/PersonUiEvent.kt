package com.personal.tmdb.detail.presentation.person

import com.personal.tmdb.core.util.MediaType

sealed interface PersonUiEvent {
    data object ChangeCollapsedBioState: PersonUiEvent
    data object ChangeBottomSheetState: PersonUiEvent
    data class SortPersonCredits(val department: String? = "", val mediaType: MediaType? = null): PersonUiEvent
}
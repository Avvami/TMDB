package com.personal.tmdb.detail.presentation.person

import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.detail.domain.models.CombinedCastCrewInfo
import com.personal.tmdb.detail.domain.models.CombinedCreditsInfo

data class PersonCreditsState(
    val personCredits: CombinedCreditsInfo? = null,
    val filteredPersonCredits: Map<String?, Map<Int, List<CombinedCastCrewInfo>>?>? = null,
    val selectedDepartment: String? = "",
    val selectedMediaType: MediaType? = null,
    val showBottomSheet: Boolean = false
)

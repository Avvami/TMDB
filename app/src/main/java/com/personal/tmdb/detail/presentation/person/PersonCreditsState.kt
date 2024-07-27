package com.personal.tmdb.detail.presentation.person

import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.detail.domain.models.CombinedCreditsInfo

data class PersonCreditsState(
    val personCredits: CombinedCreditsInfo? = null,
    val selectedDepartment: String? = null,
    val selectedMediaType: MediaType? = null
)

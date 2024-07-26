package com.personal.tmdb.detail.presentation.person

import com.personal.tmdb.detail.domain.models.PersonInfo

data class PersonState(
    val personInfo: PersonInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

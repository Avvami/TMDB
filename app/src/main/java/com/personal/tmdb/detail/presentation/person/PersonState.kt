package com.personal.tmdb.detail.presentation.person

import com.personal.tmdb.core.domain.util.UiText
import com.personal.tmdb.detail.domain.models.PersonInfo

data class PersonState(
    val personName: String,
    val personId: Int,
    val personInfo: PersonInfo? = null,
    val loading: Boolean = false,
    val errorMessage: UiText? = null
)

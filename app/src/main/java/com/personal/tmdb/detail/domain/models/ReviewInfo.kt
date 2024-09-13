package com.personal.tmdb.detail.domain.models

import com.personal.tmdb.detail.data.models.AuthorDetails
import java.time.LocalDate

data class ReviewInfo(
    val author: String?,
    val authorDetails: AuthorDetails?,
    val content: String?,
    val createdAt: LocalDate?,
    val id: String,
    val url: String?
)

package com.personal.tmdb.core.domain.util

import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.core.util.convertMediaType

fun appendToResponse(mediaType: String): String {
    val media = convertMediaType(mediaType)
    return when (media) {
        MediaType.TV -> listOf("content_ratings", "credits")
        MediaType.MOVIE -> listOf("credits", "release_dates")
        MediaType.PERSON -> listOf("images", "movie_credits", "tv_credits")
        else -> listOf()
    }.joinToString(",")
}
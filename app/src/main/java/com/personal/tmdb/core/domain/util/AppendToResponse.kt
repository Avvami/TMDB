package com.personal.tmdb.core.domain.util

import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.core.util.convertMediaType

fun appendToResponse(mediaType: String): String {
    val media = convertMediaType(mediaType)
    return when (media) {
        MediaType.TV -> listOf("content_ratings", "aggregate_credits", "similar", "recommendations", "watch/providers")
        MediaType.MOVIE -> listOf("credits", "release_dates", "similar", "recommendations", "watch/providers")
        MediaType.PERSON -> listOf("images", "movie_credits", "tv_credits")
        else -> listOf()
    }.joinToString(",")
}
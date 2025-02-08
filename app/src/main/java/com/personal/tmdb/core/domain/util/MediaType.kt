package com.personal.tmdb.core.domain.util

enum class MediaType {
    MULTI,
    TV,
    MOVIE,
    PERSON,
    EPISODE,
    UNKNOWN
}

fun convertMediaType(value: String?): MediaType {
    return when (value) {
        "multi" -> MediaType.MULTI
        "tv" -> MediaType.TV
        "movie" -> MediaType.MOVIE
        "person" -> MediaType.PERSON
        "episode" -> MediaType.EPISODE
        else -> MediaType.UNKNOWN
    }
}
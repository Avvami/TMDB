package com.personal.tmdb.core.util

enum class MediaType {
    MULTI,
    TV,
    MOVIE,
    PERSON,
    UNKNOWN
}

fun convertMediaType(value: String?): MediaType {
    return when (value) {
        "multi" -> MediaType.MULTI
        "tv" -> MediaType.TV
        "movie" -> MediaType.MOVIE
        "person" -> MediaType.PERSON
        else -> MediaType.UNKNOWN
    }
}
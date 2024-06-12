package com.personal.tmdb.core.util

enum class MediaType {
    TV,
    MOVIE,
    PERSON,
    UNKNOWN
}

fun convertMediaType(value: String): MediaType {
    return when (value) {
        "tv" -> MediaType.TV
        "movie" -> MediaType.MOVIE
        "person" -> MediaType.PERSON
        else -> MediaType.UNKNOWN
    }
}
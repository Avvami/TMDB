package com.personal.tmdb.detail.domain.util

enum class ImageType {
    PROFILES,
    STILLS,
    BACKDROPS,
    POSTERS,
    UNKNOWN
}

fun convertImageType(value: String?): ImageType {
    return when (value) {
        "profiles" -> ImageType.PROFILES
        "stills" -> ImageType.STILLS
        "backdrops" -> ImageType.BACKDROPS
        "posters" -> ImageType.POSTERS
        else -> ImageType.UNKNOWN
    }
}
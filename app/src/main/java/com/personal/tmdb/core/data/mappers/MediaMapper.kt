package com.personal.tmdb.core.data.mappers

import com.personal.tmdb.core.data.models.MediaDto
import com.personal.tmdb.core.domain.models.MediaInfo
import com.personal.tmdb.core.util.convertMediaType

fun MediaDto.toMediaInfo(): List<MediaInfo> {
    return results.map {
        MediaInfo(
            id = it.id,
            mediaType = convertMediaType(it.mediaType),
            posterPath = it.posterPath ?: it.profilePath,
            backdropPath = it.backdropPath,
            releaseDate = it.firstAirDate ?: it.releaseDate,
            title = it.title ?: it.name,
            voteAverage = it.voteAverage?.toFloat()
        )
    }
}
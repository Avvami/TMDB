package com.personal.tmdb.core.data.mappers

import com.personal.tmdb.core.data.models.MediaResponseDto
import com.personal.tmdb.core.data.models.Result
import com.personal.tmdb.core.domain.models.MediaInfo
import com.personal.tmdb.core.domain.models.MediaResponseInfo
import com.personal.tmdb.core.util.convertMediaType
import com.personal.tmdb.core.util.convertStringToDate

fun MediaResponseDto.toMediaResponseInfo(): MediaResponseInfo {
    return MediaResponseInfo(
        page = page,
        results = results.map { it.toMediaInfo() },
        totalPages = totalPages,
        totalResults = totalResults
    )
}

fun Result.toMediaInfo(): MediaInfo {
    return MediaInfo(
        backdropPath = backdropPath,
        id = id,
        knownFor = knownFor?.map { it.toMediaInfo() },
        mediaType = mediaType?.let { convertMediaType(it) },
        name = title ?: name,
        overview = if (overview.isNullOrEmpty()) null else overview,
        posterPath = posterPath ?: profilePath,
        releaseDate = convertStringToDate(
            dateString = firstAirDate?.takeIf { it.isNotBlank() } ?: releaseDate?.takeIf { it.isNotBlank() }
        ),
        voteAverage = voteAverage?.toFloat()
    )
}
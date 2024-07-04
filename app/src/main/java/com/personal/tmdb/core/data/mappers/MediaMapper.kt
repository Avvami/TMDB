package com.personal.tmdb.core.data.mappers

import com.personal.tmdb.core.data.models.MediaResponseDto
import com.personal.tmdb.core.data.models.Result
import com.personal.tmdb.core.domain.models.MediaInfo
import com.personal.tmdb.core.domain.models.MediaResponseInfo
import com.personal.tmdb.core.util.convertMediaType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun MediaResponseDto.toMediaResponseInfo(): MediaResponseInfo {
    return MediaResponseInfo(
        page = page,
        results = results.map { it.toMediaInfo() },
        totalPages = totalPages,
        totalResults = totalResults
    )
}

fun Result.toMediaInfo(): MediaInfo {
    val releaseDate: LocalDate? = try {
        val dateString = firstAirDate?.takeIf { it.isNotBlank() } ?: releaseDate?.takeIf { it.isNotBlank() }
        dateString?.let { string ->
            LocalDate.parse(string, DateTimeFormatter.ISO_LOCAL_DATE)
        }
    } catch (e: Exception) {
        null
    }
    return MediaInfo(
        backdropPath = backdropPath,
        id = id,
        mediaType = mediaType?.let { convertMediaType(it) },
        name = title ?: name,
        overview = overview,
        posterPath = posterPath ?: profilePath,
        releaseDate = releaseDate,
        voteAverage = voteAverage?.toFloat()
    )
}
package com.personal.tmdb.core.data.mappers

import com.personal.tmdb.core.data.models.MediaDto
import com.personal.tmdb.core.domain.models.MediaInfo
import com.personal.tmdb.core.util.convertMediaType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun MediaDto.toMediaInfo(): List<MediaInfo> {
    return results.map {
        val releaseDate: LocalDate? = try {
            val dateString = it.firstAirDate?.takeIf { it.isNotBlank() } ?: it.releaseDate?.takeIf { it.isNotBlank() }
            dateString?.let { string ->
                LocalDate.parse(string, DateTimeFormatter.ISO_LOCAL_DATE)
            }
        } catch (e: Exception) {
            null
        }
        MediaInfo(
            backdropPath = it.backdropPath,
            id = it.id,
            knownFor = null,
            knownForDepartment = null,
            mediaType = convertMediaType(it.mediaType),
            name = it.title ?: it.name,
            overview = it.overview,
            posterPath = it.posterPath ?: it.profilePath,
            releaseDate = releaseDate,
            voteAverage = it.voteAverage?.toFloat()
        )
    }
}
package com.personal.tmdb.core.data.mappers

import com.personal.tmdb.core.data.models.MediaDto
import com.personal.tmdb.core.domain.models.MediaInfo
import com.personal.tmdb.core.util.convertMediaType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun MediaDto.toMediaInfo(): List<MediaInfo> {
    return results.map { result ->
        val releaseDate: LocalDate? = try {
            val dateString = result.firstAirDate?.takeIf { it.isNotBlank() } ?: result.releaseDate?.takeIf { it.isNotBlank() }
            dateString?.let { string ->
                LocalDate.parse(string, DateTimeFormatter.ISO_LOCAL_DATE)
            }
        } catch (e: Exception) {
            null
        }
        MediaInfo(
            backdropPath = result.backdropPath,
            id = result.id,
            knownFor = null,
            knownForDepartment = null,
            mediaType = convertMediaType(result.mediaType),
            name = result.title ?: result.name,
            overview = result.overview,
            posterPath = result.posterPath ?: result.profilePath,
            releaseDate = releaseDate,
            voteAverage = result.voteAverage?.toFloat()
        )
    }
}
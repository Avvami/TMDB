package com.personal.tmdb.search.data.mappers

import com.personal.tmdb.core.domain.models.MediaInfo
import com.personal.tmdb.core.util.convertMediaType
import com.personal.tmdb.search.data.models.Result
import com.personal.tmdb.search.data.models.SearchDto
import com.personal.tmdb.search.domain.models.SearchInfo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun SearchDto.toSearchInfo(): SearchInfo {
    return SearchInfo(
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
        knownFor = knownFor,
        knownForDepartment = knownForDepartment,
        mediaType = mediaType?.let { convertMediaType(it) },
        name = name ?: title,
        overview = overview,
        posterPath = posterPath ?: profilePath,
        releaseDate = releaseDate,
        voteAverage = voteAverage?.toFloat()
    )
}
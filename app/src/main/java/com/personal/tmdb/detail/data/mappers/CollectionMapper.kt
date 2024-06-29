package com.personal.tmdb.detail.data.mappers

import com.personal.tmdb.core.util.convertMediaType
import com.personal.tmdb.detail.data.models.CollectionDto
import com.personal.tmdb.detail.data.models.Part
import com.personal.tmdb.detail.domain.models.CollectionInfo
import com.personal.tmdb.detail.domain.models.PartInfo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun CollectionDto.toCollectionInfo(): CollectionInfo {
    return CollectionInfo(
        backdropPath = backdropPath,
        id = id,
        name = name,
        overview = overview,
        parts = parts?.map { it.toPartInfo() },
        posterPath = posterPath
    )
}

fun Part.toPartInfo(): PartInfo {
    return PartInfo(
        backdropPath = backdropPath,
        genreIds = genreIds,
        id = id,
        mediaType = convertMediaType(mediaType),
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        releaseDate = try {
            LocalDate.parse(releaseDate, DateTimeFormatter.ISO_LOCAL_DATE)
        } catch (e: Exception) { null },
        title = title,
        voteAverage = voteAverage?.toFloat()
    )
}
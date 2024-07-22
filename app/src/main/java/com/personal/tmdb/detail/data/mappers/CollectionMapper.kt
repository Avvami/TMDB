package com.personal.tmdb.detail.data.mappers

import com.personal.tmdb.core.domain.models.MediaInfo
import com.personal.tmdb.core.util.convertMediaType
import com.personal.tmdb.detail.data.models.CollectionDto
import com.personal.tmdb.detail.data.models.Part
import com.personal.tmdb.detail.domain.models.CollectionInfo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun CollectionDto.toCollectionInfo(): CollectionInfo {
    val mediaList = parts?.map { it.toMediaInfo() }
    return CollectionInfo(
        averageRating = (mediaList?.takeIf { it.isNotEmpty() }
            ?.sumOf { it.voteAverage?.toDouble() ?: 0.0 }
            ?.div(mediaList.filter { (it.voteAverage ?: 0f) > 0f }.size)
            ?: 0.0).toFloat(),
        backdropPath = backdropPath,
        id = id,
        name = name,
        overview = overview,
        parts = mediaList,
        posterPath = posterPath
    )
}

fun Part.toMediaInfo(): MediaInfo {
    return MediaInfo(
        backdropPath = backdropPath,
        id = id,
        mediaType = convertMediaType(mediaType),
        name = title,
        overview = overview,
        posterPath = posterPath,
        releaseDate = try {
            LocalDate.parse(releaseDate, DateTimeFormatter.ISO_LOCAL_DATE)
        } catch (e: Exception) { null },
        voteAverage = voteAverage?.toFloat()
    )
}
package com.personal.tmdb.detail.data.mappers

import androidx.compose.ui.util.fastFlatMap
import com.personal.tmdb.core.domain.models.MediaInfo
import com.personal.tmdb.core.domain.util.convertMediaType
import com.personal.tmdb.detail.data.models.CollectionDto
import com.personal.tmdb.detail.data.models.Part
import com.personal.tmdb.detail.domain.models.CollectionInfo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun CollectionDto.toCollectionInfo(): CollectionInfo {
    val mediaList = parts?.map { it.toMediaInfo() }
    val genresIds = parts?.fastFlatMap { it.genreIds.orEmpty() }?.distinct()
    return CollectionInfo(
        averageRating = (mediaList?.takeIf { it.isNotEmpty() }
            ?.sumOf { it.voteAverage?.toDouble() ?: 0.0 }
            ?.div(mediaList.filter { (it.voteAverage ?: 0f) > 0f }.size)
            ?: 0.0).toFloat(),
        backdropPath = backdropPath,
        id = id,
        genresIds = genresIds,
        name = name,
        overview = if (overview.isNullOrEmpty()) null else overview,
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
        originalLanguage = originalLanguage,
        overview = if (overview.isNullOrEmpty()) null else overview,
        posterPath = posterPath,
        releaseDate = try {
            LocalDate.parse(releaseDate, DateTimeFormatter.ISO_LOCAL_DATE)
        } catch (e: Exception) { null },
        voteAverage = voteAverage?.toFloat()
    )
}
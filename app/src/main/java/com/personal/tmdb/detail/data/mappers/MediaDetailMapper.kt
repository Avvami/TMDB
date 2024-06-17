package com.personal.tmdb.detail.data.mappers

import com.personal.tmdb.detail.data.models.MediaDetailDto
import com.personal.tmdb.detail.domain.models.MediaDetailInfo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun MediaDetailDto.toMediaDetailInfo(): MediaDetailInfo {
    val releaseDate: LocalDate? = try {
        val dateString = firstAirDate?.takeIf { it.isNotBlank() } ?: releaseDate?.takeIf { it.isNotBlank() }
        dateString?.let { string ->
            LocalDate.parse(string, DateTimeFormatter.ISO_LOCAL_DATE)
        }
    } catch (e: Exception) {
        null
    }
    return MediaDetailInfo(
        backdropPath = backdropPath,
        belongsToCollection = belongsToBelongToCollection,
        credits = credits,
        genres = genres,
        id = id,
        name = title ?: name,
        networks = networks,
        numberOfEpisodes = numberOfEpisodes,
        numberOfSeasons = numberOfSeasons,
        originalName = originalName,
        overview = if (overview.isNullOrEmpty()) null else overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        runtime = if (runtime == 0) null else runtime,
        seasons = seasons,
        tagline = if (tagline.isNullOrEmpty()) null else tagline,
        voteAverage = voteAverage?.toFloat()
    )
}
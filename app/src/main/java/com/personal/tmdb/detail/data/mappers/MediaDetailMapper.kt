package com.personal.tmdb.detail.data.mappers

import com.personal.tmdb.detail.data.models.MediaDetailDto
import com.personal.tmdb.detail.domain.models.MediaDetailInfo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun MediaDetailDto.toMediaDetailInfo(): MediaDetailInfo {
    return MediaDetailInfo(
        backdropPath = backdropPath,
        belongsToCollection = belongsToBelongToCollection,
        genres = genres,
        id = id,
        networks = networks,
        numberOfEpisodes = numberOfEpisodes,
        numberOfSeasons = numberOfSeasons,
        originalName = originalName,
        overview = overview,
        posterPath = posterPath,
        releaseDate = LocalDate.parse(firstAirDate ?: releaseDate, DateTimeFormatter.ISO_LOCAL_DATE),
        runtime = runtime,
        seasons = seasons,
        tagline = tagline,
        title = title ?: name,
        voteAverage = voteAverage?.toFloat()
    )
}
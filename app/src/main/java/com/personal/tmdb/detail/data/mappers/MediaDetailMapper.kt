package com.personal.tmdb.detail.data.mappers

import com.personal.tmdb.detail.data.models.MediaDetailDto
import com.personal.tmdb.detail.domain.models.MediaDetailInfo

fun MediaDetailDto.toMediaDetailInfo(): MediaDetailInfo {
    return MediaDetailInfo(
        backdropPath = backdropPath,
        belongsToCollection = belongsToBelongToCollection,
        firstAirDate = /*LocalDateTime.parse(firstAirDate, DateTimeFormatter.ISO_LOCAL_DATE)*/firstAirDate,
        genres = genres,
        id = id,
        name = name,
        networks = networks,
        numberOfEpisodes = numberOfEpisodes,
        numberOfSeasons = numberOfSeasons,
        originalName = originalName,
        overview = overview,
        posterPath = posterPath,
        runtime = runtime,
        seasons = seasons,
        tagline = tagline,
        title = title,
        voteAverage = voteAverage
    )
}
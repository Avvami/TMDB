package com.personal.tmdb.detail.data.mappers

import com.personal.tmdb.core.domain.models.MediaInfo
import com.personal.tmdb.core.util.convertMediaType
import com.personal.tmdb.core.util.convertStringToDate
import com.personal.tmdb.detail.data.models.CombinedCastCrew
import com.personal.tmdb.detail.data.models.CombinedCredits
import com.personal.tmdb.detail.domain.models.CombinedCastCrewInfo
import com.personal.tmdb.detail.domain.models.CombinedCreditsInfo

fun CombinedCredits.toCombinedCreditsInfo(): CombinedCreditsInfo {
    return CombinedCreditsInfo(
        cast = cast?.map { it.toCombinedCastCrewInfo() }
            ?.groupBy { it.releaseDate?.year ?: 10000 }
            ?.toSortedMap(compareByDescending { it })
            ?.mapValues { entry -> entry.value.sortedByDescending { it.releaseDate } },
        castMediaInfo = cast?.map { it.toMediaInfo() }?.take(15),
        crew = crew?.map { it.toCombinedCastCrewInfo() }
            ?.groupBy { it.department }
            ?.mapValues {
                dep -> dep.value
                    .groupBy { it.releaseDate?.year ?: 10000 }
                    .toSortedMap(compareByDescending { it })
                    .mapValues { entry -> entry.value.sortedByDescending { it.releaseDate } }
            },
        crewMediaInfo = crew
            ?.groupBy { it.department }
            ?.values?.maxByOrNull { it.size }
            ?.map { it.toMediaInfo() }?.take(15)
    )
}

fun CombinedCastCrew.toCombinedCastCrewInfo(): CombinedCastCrewInfo {
    return CombinedCastCrewInfo(
        backdropPath = backdropPath,
        character = character,
        department = department,
        episodeCount = episodeCount,
        id = id,
        job = job,
        mediaType = convertMediaType(mediaType),
        name = title ?: name,
        overview = if (overview.isNullOrEmpty()) null else overview,
        posterPath = posterPath,
        releaseDate = convertStringToDate(
            dateString = firstAirDate?.takeIf { it.isNotBlank() } ?: releaseDate?.takeIf { it.isNotBlank() }
        )
    )
}

fun CombinedCastCrew.toMediaInfo(): MediaInfo {
    return MediaInfo(
        backdropPath = backdropPath,
        id = id,
        mediaType = convertMediaType(mediaType),
        name = title ?: name,
        overview = if (overview.isNullOrEmpty()) null else overview,
        posterPath = posterPath,
        releaseDate = convertStringToDate(
            dateString = firstAirDate?.takeIf { it.isNotBlank() } ?: releaseDate?.takeIf { it.isNotBlank() }
        ),
        voteAverage = voteAverage?.toFloat()
    )
}
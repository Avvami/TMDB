package com.personal.tmdb.detail.data.mappers

import com.personal.tmdb.core.data.mappers.toMediaResponseInfo
import com.personal.tmdb.detail.data.models.Credits
import com.personal.tmdb.detail.data.models.MediaDetailDto
import com.personal.tmdb.detail.domain.models.CreditsInfo
import com.personal.tmdb.detail.domain.models.MediaDetailInfo
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

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
        aggregateCredits = aggregateCredits,
        backdropPath = backdropPath,
        belongsToCollection = belongsToCollection,
        contentRatings = contentRatings,
        createdBy = createdBy,
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
        recommendations = recommendations?.toMediaResponseInfo(),
        releaseDate = releaseDate,
        releaseDates = releaseDates,
        runtime = if (runtime == 0) null else runtime,
        seasons = seasons,
        similar = similar?.toMediaResponseInfo(),
        tagline = if (tagline.isNullOrEmpty()) null else tagline,
        voteAverage = voteAverage?.toFloat(),
        watchProviders = watchProviders?.watchProvidersResults?.mapKeys { (key, _) -> Locale("", key).displayCountry}
    )
}

fun Credits.toCreditsInfo(): CreditsInfo {
    return CreditsInfo(
        cast = cast,
        crew = crew?.groupBy { it.department }
    )
}
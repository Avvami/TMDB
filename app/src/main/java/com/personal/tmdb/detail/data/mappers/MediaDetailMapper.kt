package com.personal.tmdb.detail.data.mappers

import com.personal.tmdb.core.data.mappers.toMediaResponseInfo
import com.personal.tmdb.detail.data.models.Credits
import com.personal.tmdb.detail.data.models.EpisodeToAir
import com.personal.tmdb.detail.data.models.MediaDetailDto
import com.personal.tmdb.detail.domain.models.CreditsInfo
import com.personal.tmdb.detail.domain.models.EpisodeToAirInfo
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
        lastEpisodeToAir = lastEpisodeToAir?.toEpisodeToAirInfo(),
        name = title ?: name,
        networks = networks,
        nextEpisodeToAir = nextEpisodeToAir?.toEpisodeToAirInfo(),
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
        voteCount = voteCount,
        watchProviders = watchProviders?.watchProvidersResults?.mapKeys { (key, _) -> Locale("", key).displayCountry}
    )
}

fun Credits.toCreditsInfo(): CreditsInfo {
    return CreditsInfo(
        cast = cast,
        crew = crew?.groupBy { it.department },
        guestStars = guestStars
    )
}

fun EpisodeToAir.toEpisodeToAirInfo(): EpisodeToAirInfo {
    val airDate = try {
        airDate?.let { string ->
            LocalDate.parse(string, DateTimeFormatter.ISO_LOCAL_DATE)
        }
    } catch (e: Exception) {
        null
    }
    return EpisodeToAirInfo(
        airDate = airDate,
        episodeNumber = episodeNumber,
        episodeType = episodeType,
        id = id,
        name = if (name.isNullOrEmpty()) null else name,
        overview = if (overview.isNullOrEmpty()) null else overview,
        productionCode = productionCode,
        runtime = if (runtime == 0) null else runtime,
        seasonNumber = seasonNumber,
        showId = showId,
        stillPath = stillPath,
        voteAverage = voteAverage?.toFloat()
    )
}
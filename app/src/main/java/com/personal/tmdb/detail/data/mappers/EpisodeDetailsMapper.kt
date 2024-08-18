package com.personal.tmdb.detail.data.mappers

import com.personal.tmdb.detail.data.models.EpisodeDetailsDto
import com.personal.tmdb.detail.domain.models.EpisodeDetailsInfo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun EpisodeDetailsDto.toEpisodeDetailsInfo(): EpisodeDetailsInfo {
    val airDate: LocalDate? = try {
        val dateString = airDate?.takeIf { it.isNotBlank() }
        dateString?.let { string ->
            LocalDate.parse(string, DateTimeFormatter.ISO_LOCAL_DATE)
        }
    } catch (e: Exception) {
        null
    }
    return EpisodeDetailsInfo(
        airDate = airDate,
        crew = crew,
        episodeNumber = episodeNumber,
        guestStars = guestStars,
        id = id,
        images = images,
        name = if (name.isNullOrEmpty()) null else name,
        overview = if (overview.isNullOrEmpty()) null else overview,
        productionCode = productionCode,
        runtime = if (runtime == 0) null else runtime,
        seasonNumber = seasonNumber,
        stillPath = stillPath,
        translations = translations,
        voteAverage = voteAverage?.toFloat(),
        voteCount = voteCount
    )
}
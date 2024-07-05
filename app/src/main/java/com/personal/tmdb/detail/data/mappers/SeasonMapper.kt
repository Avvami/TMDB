package com.personal.tmdb.detail.data.mappers

import com.personal.tmdb.detail.data.models.Episode
import com.personal.tmdb.detail.data.models.SeasonDto
import com.personal.tmdb.detail.domain.models.EpisodeInfo
import com.personal.tmdb.detail.domain.models.SeasonInfo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun SeasonDto.toSeasonInfo(): SeasonInfo {
    val airDate: LocalDate? = try {
        val dateString = airDate?.takeIf { it.isNotBlank() }
        dateString?.let { string ->
            LocalDate.parse(string, DateTimeFormatter.ISO_LOCAL_DATE)
        }
    } catch (e: Exception) {
        null
    }
    return SeasonInfo(
        airDate = airDate,
        episodes = episodes?.map { it.toEpisodeInfo() },
        idString = idString,
        id = id,
        name = name,
        overview = if (overview.isNullOrEmpty()) null else overview,
        posterPath = posterPath,
        seasonNumber = seasonNumber,
        voteAverage = voteAverage?.toFloat()
    )
}

fun Episode.toEpisodeInfo(): EpisodeInfo {
    val airDate: LocalDate? = try {
        val dateString = airDate?.takeIf { it.isNotBlank() }
        dateString?.let { string ->
            LocalDate.parse(string, DateTimeFormatter.ISO_LOCAL_DATE)
        }
    } catch (e: Exception) {
        null
    }
    return EpisodeInfo(
        airDate = airDate,
        episodeNumber = episodeNumber,
        episodeType = episodeType,
        id = id,
        name = name,
        overview = if (overview.isNullOrEmpty()) null else overview,
        runtime = if (runtime == 0) null else runtime,
        stillPath = stillPath,
        voteAverage = voteAverage?.toFloat()
    )
}
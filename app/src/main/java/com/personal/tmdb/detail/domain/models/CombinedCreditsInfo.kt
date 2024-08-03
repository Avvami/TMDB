package com.personal.tmdb.detail.domain.models

import com.personal.tmdb.core.domain.models.MediaInfo
import com.personal.tmdb.core.util.MediaType
import java.time.LocalDate

data class CombinedCreditsInfo(
    val credits: Map<String?, Map<Int, List<CombinedCastCrewInfo>>?>?,
    val castMediaInfo: List<MediaInfo>?,
    val crewMediaInfo: List<MediaInfo>?,
)

data class CombinedCastCrewInfo(
    val backdropPath: String?,
    val department: String?,
    val id: Int,
    val jobs: List<Jobs>?,
    val mediaType: MediaType,
    val name: String?,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: LocalDate?,
)

data class Jobs(
    val character: String?,
    val episodeCount: Int?,
    val job: String?
)

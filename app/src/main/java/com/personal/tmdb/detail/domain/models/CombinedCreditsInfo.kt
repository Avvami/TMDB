package com.personal.tmdb.detail.domain.models

import com.personal.tmdb.core.domain.models.MediaInfo
import com.personal.tmdb.core.util.MediaType
import java.time.LocalDate

data class CombinedCreditsInfo(
    val cast: Map<Int, List<CombinedCastCrewInfo>?>?,
    val castMediaInfo: List<MediaInfo>?,
    val crew: Map<String?, Map<Int, List<CombinedCastCrewInfo>?>?>?,
    val crewMediaInfo: List<MediaInfo>?,
)

data class CombinedCastCrewInfo(
    val backdropPath: String?,
    val character: String?,
    val department: String?,
    val episodeCount: Int?,
    val id: Int,
    val job: String?,
    val mediaType: MediaType,
    val name: String?,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: LocalDate?,
)

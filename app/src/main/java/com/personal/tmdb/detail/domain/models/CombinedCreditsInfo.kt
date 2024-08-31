package com.personal.tmdb.detail.domain.models

import androidx.compose.runtime.Stable
import com.personal.tmdb.core.domain.models.MediaInfo
import com.personal.tmdb.core.util.MediaType
import java.time.LocalDate
import java.util.UUID

data class CombinedCreditsInfo(
    val credits: Map<String?, Map<Int, List<CombinedCastCrewInfo>>?>?,
    val castMediaInfo: List<MediaInfo>?,
    val crewMediaInfo: List<MediaInfo>?,
)

@Stable
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
    val uniqueId: String = UUID.randomUUID().toString()
)

data class Jobs(
    val character: String?,
    val episodeCount: Int?,
    val job: String?
)

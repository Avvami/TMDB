package com.personal.tmdb.search.domain.models

import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.search.data.models.KnownFor

data class ResultInfo(
    val backdropPath: String?,
    val id: Int,
    val knownFor: List<KnownFor?>?,
    val knownForDepartment: String?,
    val mediaType: MediaType?,
    val name: String?,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: String?,
    val voteAverage: Float?
)

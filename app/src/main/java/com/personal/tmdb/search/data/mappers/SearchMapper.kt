package com.personal.tmdb.search.data.mappers

import com.personal.tmdb.core.util.convertMediaType
import com.personal.tmdb.search.data.models.Result
import com.personal.tmdb.search.data.models.SearchDto
import com.personal.tmdb.search.domain.models.ResultInfo
import com.personal.tmdb.search.domain.models.SearchInfo

fun SearchDto.toSearchInfo(): SearchInfo {
    return SearchInfo(
        page = page,
        results = results.map { it?.toResultInfo() },
        totalPages = totalPages,
        totalResults = totalResults
    )
}

fun Result.toResultInfo(): ResultInfo {
    return ResultInfo(
        backdropPath = backdropPath,
        id = id,
        knownFor = knownFor,
        knownForDepartment = knownForDepartment,
        mediaType = mediaType?.let { convertMediaType(it) },
        name = name ?: title,
        overview = overview,
        posterPath = posterPath ?: profilePath,
        releaseDate = firstAirDate ?: releaseDate,
        voteAverage = voteAverage?.toFloat()
    )
}
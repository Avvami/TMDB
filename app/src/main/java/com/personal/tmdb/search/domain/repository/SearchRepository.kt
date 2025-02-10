package com.personal.tmdb.search.domain.repository

import com.personal.tmdb.core.domain.models.MediaResponseInfo
import com.personal.tmdb.core.domain.util.DataError
import com.personal.tmdb.core.domain.util.Result
import com.personal.tmdb.core.domain.util.TimeWindow

interface SearchRepository {

    suspend fun searchFor(
        searchType: String,
        query: String,
        includeAdult: Boolean? = null,
        language: String? = null,
        page: Int
    ): Result<MediaResponseInfo, DataError.Remote>

    suspend fun getTrendingList(timeWindow: TimeWindow, language: String? = null): Result<MediaResponseInfo, DataError.Remote>

    suspend fun getPopularPeopleList(mediaType: String, language: String? = null): Result<MediaResponseInfo, DataError.Remote>
}
package com.personal.tmdb.search.domain.repository

import com.personal.tmdb.core.domain.models.MediaResponseInfo
import com.personal.tmdb.core.domain.util.Resource
import com.personal.tmdb.core.domain.util.TimeWindow

interface SearchRepository {

    suspend fun searchFor(searchType: String, query: String, includeAdult: Boolean? = null, language: String? = null, page: Int): Resource<MediaResponseInfo>

    suspend fun getTrendingList(timeWindow: TimeWindow, language: String? = null): Resource<MediaResponseInfo>

    suspend fun getPopularPeopleList(mediaType: String, language: String? = null): Resource<MediaResponseInfo>
}
package com.personal.tmdb.search.domain.repository

import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.search.domain.models.SearchInfo

interface SearchRepository {

    suspend fun searchFor(searchType: String, query: String, includeAdult: Boolean? = null, language: String? = null, page: Int): Resource<SearchInfo>
}
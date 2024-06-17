package com.personal.tmdb.search.data.repository

import com.personal.tmdb.core.data.remote.TmdbApi
import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.search.data.mappers.toSearchInfo
import com.personal.tmdb.search.domain.models.SearchInfo
import com.personal.tmdb.search.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val tmdbApi: TmdbApi
): SearchRepository {
    override suspend fun searchFor(
        searchType: String,
        query: String,
        includeAdult: Boolean?,
        language: String?,
        page: Int
    ): Resource<SearchInfo> {
        return try {
            Resource.Success(
                data = tmdbApi.searchFor(
                    searchType = searchType,
                    query = query,
                    includeAdult,
                    language,
                    page
                ).toSearchInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown")
        }
    }
}
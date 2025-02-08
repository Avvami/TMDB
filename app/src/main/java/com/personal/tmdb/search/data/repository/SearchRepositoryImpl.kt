package com.personal.tmdb.search.data.repository

import com.personal.tmdb.core.data.mappers.toMediaResponseInfo
import com.personal.tmdb.core.data.remote.TmdbApi
import com.personal.tmdb.core.domain.models.MediaResponseInfo
import com.personal.tmdb.core.domain.util.Resource
import com.personal.tmdb.core.domain.util.TimeWindow
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
    ): Resource<MediaResponseInfo> {
        return try {
            Resource.Success(
                data = tmdbApi.searchFor(
                    searchType = searchType,
                    query = query,
                    includeAdult,
                    language,
                    page
                ).toMediaResponseInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown")
        }
    }

    override suspend fun getTrendingList(
        timeWindow: TimeWindow,
        language: String?
    ): Resource<MediaResponseInfo> {
        return try {
            Resource.Success(
                data = tmdbApi.getTrendingList(timeWindow.name.lowercase(), language).toMediaResponseInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown")
        }
    }

    override suspend fun getPopularPeopleList(
        mediaType: String,
        language: String?
    ): Resource<MediaResponseInfo> {
        return try {
            Resource.Success(
                data = tmdbApi.getPopularList(mediaType, language, 1).toMediaResponseInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown")
        }
    }
}
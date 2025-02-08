package com.personal.tmdb.home.data.repository

import com.personal.tmdb.core.data.mappers.toMediaResponseInfo
import com.personal.tmdb.core.data.remote.TmdbApi
import com.personal.tmdb.core.domain.models.MediaResponseInfo
import com.personal.tmdb.core.domain.util.Resource
import com.personal.tmdb.core.domain.util.TimeWindow
import com.personal.tmdb.home.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val tmdbApi: TmdbApi
): HomeRepository {
    override suspend fun getTrendingList(timeWindow: TimeWindow, language: String?): Resource<MediaResponseInfo> {
        return try {
            Resource.Success(
                data = tmdbApi.getTrendingList(timeWindow.name.lowercase(), language).toMediaResponseInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown")
        }
    }

    override suspend fun getNowPlaying(language: String?): Resource<MediaResponseInfo> {
        return try {
            Resource.Success(
                data = tmdbApi.getNowPlaying(language).toMediaResponseInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown")
        }
    }
}
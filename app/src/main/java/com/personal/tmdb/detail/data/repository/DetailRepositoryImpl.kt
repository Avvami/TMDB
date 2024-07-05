package com.personal.tmdb.detail.data.repository

import com.personal.tmdb.core.data.remote.TmdbApi
import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.detail.data.mappers.toCollectionInfo
import com.personal.tmdb.detail.data.mappers.toMediaDetailInfo
import com.personal.tmdb.detail.data.mappers.toSeasonInfo
import com.personal.tmdb.detail.domain.models.CollectionInfo
import com.personal.tmdb.detail.domain.models.MediaDetailInfo
import com.personal.tmdb.detail.domain.models.SeasonInfo
import com.personal.tmdb.detail.domain.repository.DetailRepository
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val tmdbApi: TmdbApi
): DetailRepository {
    override suspend fun getMediaDetail(
        mediaType: String,
        mediaId: Int,
        language: String?,
        appendToResponse: String?
    ): Resource<MediaDetailInfo> {
        return try {
            Resource.Success(
                data = tmdbApi.getMovieTvDetail(
                    mediaType = mediaType,
                    mediaId = mediaId,
                    language = language,
                    appendToResponse = appendToResponse
                ).toMediaDetailInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown")
        }
    }

    override suspend fun getCollection(
        collectionId: Int,
        language: String?
    ): Resource<CollectionInfo> {
        return try {
            Resource.Success(
                data = tmdbApi.getCollection(
                    collectionId = collectionId,
                    language = language
                ).toCollectionInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown")
        }
    }

    override suspend fun getSeasonDetail(
        seriesId: Int,
        seasonNumber: Int,
        language: String?
    ): Resource<SeasonInfo> {
        return try {
            Resource.Success(
                data = tmdbApi.getSeasonDetail(seriesId, seasonNumber, language).toSeasonInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown")
        }
    }
}
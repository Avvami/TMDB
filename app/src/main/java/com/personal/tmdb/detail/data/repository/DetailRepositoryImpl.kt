package com.personal.tmdb.detail.data.repository

import com.personal.tmdb.core.data.remote.TmdbApi
import com.personal.tmdb.core.util.MediaType
import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.detail.data.mappers.toMediaDetailInfo
import com.personal.tmdb.detail.domain.models.MediaDetailInfo
import com.personal.tmdb.detail.domain.repository.DetailRepository
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val tmdbApi: TmdbApi
): DetailRepository {
    override suspend fun getMediaDetail(
        mediaType: MediaType,
        mediaId: Int,
        language: String?,
        appendToResponse: String?
    ): Resource<MediaDetailInfo> {
        return try {
            Resource.Success(
                data = tmdbApi.getMovieTvDetial(
                    mediaType = mediaType.name.lowercase(),
                    mediaId = mediaId,
                    language = language,
                    appendToResponse = appendToResponse
                ).toMediaDetailInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Uknown")
        }
    }
}
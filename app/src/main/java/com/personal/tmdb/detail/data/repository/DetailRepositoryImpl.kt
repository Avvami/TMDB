package com.personal.tmdb.detail.data.repository

import com.personal.tmdb.core.data.remote.TmdbApi
import com.personal.tmdb.core.util.Resource
import com.personal.tmdb.detail.data.mappers.toCollectionInfo
import com.personal.tmdb.detail.data.mappers.toCreditsInfo
import com.personal.tmdb.detail.data.mappers.toEpisodeDetailsInfo
import com.personal.tmdb.detail.data.mappers.toMediaDetailInfo
import com.personal.tmdb.detail.data.mappers.toPersonInfo
import com.personal.tmdb.detail.data.mappers.toReviewsResponseInfo
import com.personal.tmdb.detail.data.mappers.toSeasonInfo
import com.personal.tmdb.detail.data.models.Images
import com.personal.tmdb.detail.domain.models.CollectionInfo
import com.personal.tmdb.detail.domain.models.CreditsInfo
import com.personal.tmdb.detail.domain.models.EpisodeDetailsInfo
import com.personal.tmdb.detail.domain.models.MediaDetailInfo
import com.personal.tmdb.detail.domain.models.PersonInfo
import com.personal.tmdb.detail.domain.models.ReviewsResponseInfo
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
        appendToResponse: String?,
        includeImageLanguage: String?
    ): Resource<MediaDetailInfo> {
        return try {
            Resource.Success(
                data = tmdbApi.getMovieTvDetail(
                    mediaType = mediaType,
                    mediaId = mediaId,
                    language = language,
                    appendToResponse = appendToResponse,
                    includeImageLanguage = includeImageLanguage
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

    override suspend fun getSeasonDetails(
        seriesId: Int,
        seasonNumber: Int,
        language: String?
    ): Resource<SeasonInfo> {
        return try {
            Resource.Success(
                data = tmdbApi.getSeasonDetails(seriesId, seasonNumber, language).toSeasonInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown")
        }
    }

    override suspend fun getCast(
        mediaType: String,
        mediaId: Int,
        method: String,
        language: String?
    ): Resource<CreditsInfo> {
        return try {
            Resource.Success(
                data = tmdbApi.getCredits(mediaType, mediaId, method, language).toCreditsInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown")
        }
    }

    override suspend fun getEpisodeCast(
        mediaId: Int,
        seasonNumber: Int,
        episodeNumber: Int,
        language: String?
    ): Resource<CreditsInfo> {
        return try {
            Resource.Success(
                data = tmdbApi.getEpisodeCredits(mediaId, seasonNumber, episodeNumber, language).toCreditsInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown")
        }
    }

    override suspend fun getPerson(
        personId: Int,
        language: String?,
        appendToResponse: String?
    ): Resource<PersonInfo> {
        return try {
            Resource.Success(
                data = tmdbApi.getPerson(personId, language, appendToResponse).toPersonInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown")
        }
    }

    override suspend fun getEpisodeDetails(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int,
        language: String?,
        appendToResponse: String?,
        includeImageLanguage: String?
    ): Resource<EpisodeDetailsInfo> {
        return try {
            Resource.Success(
                data = tmdbApi.getEpisodeDetails(
                    seriesId,
                    seasonNumber,
                    episodeNumber,
                    language,
                    appendToResponse,
                    includeImageLanguage
                ).toEpisodeDetailsInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown")
        }
    }

    override suspend fun getImages(
        path: String,
        language: String?,
        includeImageLanguage: String?
    ): Resource<Images> {
        return try {
            Resource.Success(
                data = tmdbApi.getImages(
                    path,
                    language,
                    includeImageLanguage
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown")
        }
    }

    override suspend fun getReviews(
        mediaType: String,
        mediaId: Int,
        page: Int,
        language: String?
    ): Resource<ReviewsResponseInfo> {
        return try {
            Resource.Success(
                data = tmdbApi.getReviews(
                    mediaType, mediaId, page, language
                ).toReviewsResponseInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown")
        }
    }
}